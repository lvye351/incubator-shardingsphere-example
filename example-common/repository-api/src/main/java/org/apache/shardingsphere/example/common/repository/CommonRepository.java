/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.example.common.repository;

import java.util.List;

import org.apache.shardingsphere.example.common.entity.Order;

public interface CommonRepository<T, P> {
    
    /**
     * Create table if not exist.
     */
    void createTableIfNotExists();
    
    /**
     * Drop table.
     */
    void dropTable();
    
    /**
     * Truncate table.
     */
    void truncateTable();
    
    /**
     * insert one entity.
     * @param entity entity
     * @return count or primary key
     */
    Long insert(T entity);
    /**
     * 
     * @param orders
     */
    void batchInserts(List<Order> orders);
    /**
     * 
     * @param orders
     */
    void batchUpdates(List<Order> orders);
    
    /**
     * Do delete.
     * @param key key
     */
    void delete(P key);
    
    /**
     * select all.
     * @return list of entity
     */
    List<T> selectAll();
}
