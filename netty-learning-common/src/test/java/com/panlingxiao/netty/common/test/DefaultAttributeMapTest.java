package com.panlingxiao.netty.common.test;

import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.DefaultAttributeMap;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * Created by panlingxiao on 2016/11/12.
 */
public class DefaultAttributeMapTest {

    private DefaultAttributeMap map;

    @Before
    public void setup() {
        map = new DefaultAttributeMap();
    }

    @Test
    public void testGetSetString() {
        AttributeKey<String> key = AttributeKey.valueOf("Nothing");
        Attribute<String> one = map.attr(key);
        assertSame(one, map.attr(key));

        one.setIfAbsent("Whoohoo");
        assertSame("Whoohoo", one.get());

        one.setIfAbsent("What");
        assertNotSame("What", one.get());

        one.remove();
        assertNull(one.get());
    }
}
