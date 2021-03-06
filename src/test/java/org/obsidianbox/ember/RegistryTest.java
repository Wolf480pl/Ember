/**
 * This file is part of Ember, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014 ObsidianBox <http://obsidianbox.org/>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.obsidianbox.ember;

import com.flowpowered.commons.Named;
import org.junit.Test;
import org.obsidianbox.ember.world.storage.TypeIdNamedRegistry;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class RegistryTest {
    private final TypeIdNamedRegistry<TestA> regA = new TypeIdNamedRegistry<>(TestA.class);

    @Test
    public void testRegistry() {
        assertTrue(!regA.get((short) 0).isPresent());
        final TestA testA = new TestA();
        final TestA addedTestA = regA.add(testA);
        assertTrue(testA.equals(addedTestA));
        assertTrue(regA.get((short) 0).get().equals(testA));
        assertTrue(regA.get("testA").get().getName().equals(testA.getName()));
    }
}

final class TestA implements Named {
    @Override
    public String getName() {
        return "testA";
    }
}
