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
package org.obsidianbox.ember.network.message;

import com.flowpowered.networking.Message;

enum HandshakeState {
    LOGIN((byte) 0),
    STATUS((byte) 1);
    private final byte flag;

    HandshakeState(byte flag) {
        this.flag = flag;
    }

    public byte value() {
        return flag;
    }
}

public class HandshakeMessage implements Message {
    public final byte version;
    public final HandshakeState state;

    public HandshakeMessage(byte version, HandshakeState state) {
        this.version = version;
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HandshakeMessage that = (HandshakeMessage) o;

        return version == that.version && state == that.state;
    }

    @Override
    public int hashCode() {
        int result = (int) version;
        result = 31 * result + state.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "HandshakeMessage{" +
                "version=" + version +
                ", state=" + state +
                '}';
    }
}