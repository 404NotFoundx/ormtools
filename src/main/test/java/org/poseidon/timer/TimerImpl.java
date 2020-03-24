package org.poseidon.timer;

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
 * Creates on TODO DATE
 */

import org.raniaia.poseidon.framework.timer.Timer;

/**
 * Copyright: Create by tiansheng on 2019/12/6 21:53
 */
public class TimerImpl implements Timer {

    private int count;

    @Override
    public void run() {
        if (count == 3) {
            interrupt();
            System.out.println("中断处理程序");

        }
        count++;
        System.out.println("timer execute...");
    }

    @Override
    public long time() {
        return 1000;
    }

}
