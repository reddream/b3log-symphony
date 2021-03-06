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
package org.b3log.symphony.repository;

import org.b3log.latke.repository.AbstractRepository;
import org.b3log.symphony.model.Comment;

/**
 * Comment repository.
 *
 * @author <a href="mailto:DL88250@gmail.com">Liang Ding</a>
 * @version 1.0.0.0, Oct 7, 2012
 * @since 0.2.0
 */
public final class CommentRepository extends AbstractRepository {

    /**
     * Singleton.
     */
    private static final CommentRepository SINGLETON = new CommentRepository(Comment.COMMENT);

    /**
     * Private constructor.
     * 
     * @param name the specified name
     */
    private CommentRepository(final String name) {
        super(name);
    }

    /**
     * Gets the {@link CommentRepository} singleton.
     *
     * @return the singleton
     */
    public static CommentRepository getInstance() {
        return SINGLETON;
    }
}
