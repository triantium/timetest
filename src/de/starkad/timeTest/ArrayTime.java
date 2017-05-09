/*
 * The MIT License
 *
 * Copyright 2017 Manuel Müller.
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
package de.starkad.timeTest;

import de.starkad.worker.Worker;

/**
 *
 * @author Manuel Müller
 */
public class ArrayTime extends AbstractTimeTest {

    private Worker[] testObjects;

    public ArrayTime(int testSize, int iterations) {
        super(testSize, iterations);
        fillWhateverIsUsed(testSize);
    }

    @Override
    protected long doLamdaRun() {
        return 0;
    }

    @Override
    protected long doIteratorRun() {
        long startTime = System.nanoTime();
        for (Worker object : testObjects) {
            object.doSomething();
        }
        return (System.nanoTime() - startTime);
    }

    @Override
    protected long doIndexRun() {
        long startTime = System.nanoTime();
        for (int i = 0; i < testSize; i++) {
            testObjects[i].doSomething();
        }
        return (System.nanoTime() - startTime);
    }

    @Override
    protected void fillWhateverIsUsed(int size) {
        testObjects = new Worker[size];
        for (int i = 0; i < size; i++) {
            testObjects[i] = new Worker();
        }
    }

}
