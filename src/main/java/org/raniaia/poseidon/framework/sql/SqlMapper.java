package org.raniaia.poseidon.framework.sql;

/*
 * Copyright (C) 2020 Tiansheng All rights reserved.
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

/*
 * Creates on 2019/12/23.
 */

import org.raniaia.poseidon.BeansManager;
import org.raniaia.poseidon.framework.container.PrecompileContainer;
import org.raniaia.poseidon.components.db.JdbcSupport;
import org.raniaia.poseidon.framework.provide.Valid;
import org.raniaia.poseidon.framework.sql.xml.build.PrecompiledClass;
import org.raniaia.poseidon.framework.sql.xml.build.PrecompiledMethod;

import java.util.*;

/**
 * @author tiansheng
 */
public class SqlMapper {

    private Converter converter = BeansManager.newInstance(Converter.class);
    private static PrecompileContainer container = PrecompileContainer.getContainer();

    @Valid
    private JdbcSupport jdbcSupport;

    /**
     * class
     */
    private PrecompiledClass classValue;

    public SqlMapper() {
    }

    public SqlMapper(String name) {
        this.classValue = container.getValue(name);
    }

    /**
     * Is exist mapper.
     */
    public static boolean isMapper(String name){
        return container.getValue(name) != null;
    }

    public SqlExecute build(String methodName, Parameter parameter) {
        SqlExecute execute = null;
        try {
            Map<String, Object> parameterMap = new HashMap<>();
            parameter.loader(parameterMap);
            PrecompiledMethod precompiledMethod = classValue.getPrecompiledMethod(methodName);
            // 如果没有Method方法就创建一个
            if (precompiledMethod.getIMethod() == null) {
                converter.conversion(precompiledMethod, parameterMap, classValue.getFullName());
            }
            List param = new ArrayList();
            String sql = precompiledMethod.invoke(parameterMap, param);
            execute = new SqlExecute(sql, param.toArray(), precompiledMethod.getResult(),
                    jdbcSupport, precompiledMethod.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return execute;
    }

    public static SqlMapper getMapper(String name) {
        SqlMapper mapperBean = BeansManager.get(name);
        if (mapperBean != null) {
            return mapperBean;
        }
        BeansManager.submitBean(name, BeansManager.newInstance(SqlMapper.class, name));
        return BeansManager.get(name);
    }

}
