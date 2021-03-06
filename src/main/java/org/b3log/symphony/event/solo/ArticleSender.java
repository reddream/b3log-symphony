/*
 * Copyright (c) 2012, B3log Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.b3log.symphony.event.solo;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.b3log.latke.Keys;
import org.b3log.latke.event.AbstractEventListener;
import org.b3log.latke.event.Event;
import org.b3log.latke.event.EventException;
import org.b3log.latke.servlet.HTTPRequestMethod;
import org.b3log.latke.urlfetch.HTTPRequest;
import org.b3log.latke.urlfetch.URLFetchService;
import org.b3log.latke.urlfetch.URLFetchServiceFactory;
import org.b3log.latke.util.Strings;
import org.b3log.symphony.event.EventTypes;
import org.b3log.symphony.model.Article;
import org.b3log.symphony.model.Common;
import org.b3log.symphony.model.UserExt;
import org.b3log.symphony.service.UserQueryService;
import org.json.JSONObject;

/**
 * Sends article to client.
 *
 * @author <a href="mailto:DL88250@gmail.com">Liang Ding</a>
 * @version 1.0.0.0, Oct 12, 2012
 * @since 0.2.0
 */
public final class ArticleSender extends AbstractEventListener<JSONObject> {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ArticleSender.class.getName());
    /**
     * User query service.
     */
    private UserQueryService userQueryService = UserQueryService.getInstance();
    /**
     * URL fetch service.
     */
    private URLFetchService urlFetchService = URLFetchServiceFactory.getURLFetchService();

    @Override
    public void action(final Event<JSONObject> event) throws EventException {
        final JSONObject data = event.getData();
        LOGGER.log(Level.FINER, "Processing an event[type={0}, data={1}] in listener[className={2}]",
                   new Object[]{event.getType(), data, ArticleSender.class.getName()});
        try {
            if (data.optBoolean(Common.FROM_CLIENT)) {
                return;
            }

            final JSONObject originalArticle = data.getJSONObject(Article.ARTICLE);

            if (!originalArticle.optBoolean(Article.ARTICLE_SYNC_TO_CLIENT)) {
                return;
            }

            final String authorId = originalArticle.optString(Article.ARTICLE_AUTHOR_ID);
            final JSONObject author = userQueryService.getUser(authorId);
            final String clientURL = author.optString(UserExt.USER_B3_CLIENT_ADD_ARTICLE_URL);

            if (Strings.isEmptyOrNull(clientURL)) {
                return;
            }

            final HTTPRequest httpRequest = new HTTPRequest();
            httpRequest.setURL(new URL(clientURL));
            httpRequest.setRequestMethod(HTTPRequestMethod.PUT);
            final JSONObject requestJSONObject = new JSONObject();
            final JSONObject article = new JSONObject(originalArticle, new String[]{
                        Article.ARTICLE_CONTENT,
                        Article.ARTICLE_TAGS,
                        Article.ARTICLE_TITLE,
                        Keys.OBJECT_ID
                    });

            article.put(UserExt.USER_B3_KEY, author.optString(UserExt.USER_B3_KEY));
            article.put(Article.ARTICLE_EDITOR_TYPE, "CodeMirror-Markdown");

            requestJSONObject.put(Article.ARTICLE, article);
            httpRequest.setPayload(requestJSONObject.toString().getBytes("UTF-8"));

            urlFetchService.fetchAsync(httpRequest);
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, "Sends an article to client error: {0}", e.getMessage());
        }

        LOGGER.log(Level.FINER, "Sent an article to client");
    }

    /**
     * Gets the event type {@linkplain EventTypes#ADD_ARTICLE}.
     * 
     * @return event type
     */
    @Override
    public String getEventType() {
        return EventTypes.ADD_ARTICLE;
    }
}
