package org.laniakeamly.poseidon.framework.container;

/**
 * Create by 2BKeyboard on 2019/12/19 11:30
 */
public interface Container {

    /**
     * 获取一个对象
     * @param name
     * @return
     */
    <T> T getValue(String name);

}
