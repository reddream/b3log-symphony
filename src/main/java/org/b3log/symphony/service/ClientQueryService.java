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
package org.b3log.symphony.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.b3log.latke.Keys;
import org.b3log.latke.repository.FilterOperator;
import org.b3log.latke.repository.PropertyFilter;
import org.b3log.latke.repository.Query;
import org.b3log.latke.repository.RepositoryException;
import org.b3log.latke.service.LangPropsService;
import org.b3log.latke.service.ServiceException;
import org.b3log.symphony.model.Client;
import org.b3log.symphony.repository.ClientRepository;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Client query service.
 *
 * @author <a href="mailto:DL88250@gmail.com">Liang Ding</a>
 * @version 1.0.0.0, Oct 18, 2012
 * @since 0.2.0
 */
public final class ClientQueryService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ClientQueryService.class.getName());
    /**
     * Singleton.
     */
    private static final ClientQueryService SINGLETON = new ClientQueryService();
    /**
     * Client repository.
     */
    private ClientRepository clientRepository = ClientRepository.getInstance();
    /**
     * Language service.
     */
    private LangPropsService langPropsService = LangPropsService.getInstance();

    /**
     * Adds a client by the specified administrator email.
     *
     * @param adminEmail the specified administrator email
     * @return client,returns {@code null} if not found
     * @throws ServiceException service exception
     */
    public JSONObject getClientByAdminEmail(final String adminEmail) throws ServiceException {
        final Query query = new Query().setFilter(new PropertyFilter(Client.CLIENT_ADMIN_EMAIL, FilterOperator.EQUAL, adminEmail));
        try {
            final JSONObject result = clientRepository.get(query);
            final JSONArray array = result.optJSONArray(Keys.RESULTS);

            if (0 == array.length()) {
                return null;
            }

            return array.optJSONObject(0);
        } catch (final RepositoryException e) {
            LOGGER.log(Level.SEVERE, "Gets client failed", e);
            throw new ServiceException(e);
        }
    }

    /**
     * Gets the {@link ClientQueryService} singleton.
     *
     * @return the singleton
     */
    public static ClientQueryService getInstance() {
        return SINGLETON;
    }

    /**
     * Private constructor.
     */
    private ClientQueryService() {
    }
}
