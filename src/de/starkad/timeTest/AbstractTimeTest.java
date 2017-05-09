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

/**
 *
 * @author Manuel Müller
 */
public abstract class AbstractTimeTest {

    private int runs = 0;

    private long lastIteratorTime = 0;
    private long lastIndexedTime = 0;
    private long lastLambdaTime = -1;

    private long medianIteratorTime = -1;
    private long medianIndexedTime = -1;
    private long medianLambdaTime = -1;

    private long totalIteratorTime = 0;
    private long totalIndexedTime = 0;
    private long totalLambdaTime = 0;

    protected int testSize;
    private int iterations;
    private final String className;

    public AbstractTimeTest(int testSize, int iterations) {
        this.testSize = testSize;
        this.iterations = iterations;
        className = this.getClass().getSimpleName();
        reset();
    }

    //if not possible simply return 0;
    protected abstract long doLamdaRun();

    protected abstract long doIteratorRun();

    protected abstract long doIndexRun();

    protected abstract void fillWhateverIsUsed(int size);

    public int getTestSize() {
        return testSize;
    }

    public void setTestSize(int testSize) {
        this.testSize = testSize;
        fillWhateverIsUsed(testSize);
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public void reset() {
        runs = 0;

        lastIteratorTime = 0;
        lastIndexedTime = 0;
        lastLambdaTime = -1;

        medianIteratorTime = -1;
        medianIndexedTime = -1;
        medianLambdaTime = -1;

        totalIteratorTime = 0;
        totalIndexedTime = 0;
        totalLambdaTime = 0;

    }

    private void resetLastCounter() {
        lastIndexedTime = 0;
        lastLambdaTime = 0;
        lastIteratorTime = 0;
    }

    private void calculateMedians() {
        medianIndexedTime = totalIndexedTime / runs;
        medianIteratorTime = totalIteratorTime / runs;
        medianLambdaTime = totalLambdaTime / runs;
    }

    private void doOneRun() {
        resetLastCounter();
        lastIndexedTime = doIndexRun();
        lastIteratorTime = doIteratorRun();
        lastLambdaTime = doLamdaRun();
        totalIndexedTime += lastIndexedTime;
        totalIteratorTime += lastIteratorTime;
        totalLambdaTime += lastLambdaTime;
        runs++;
        calculateMedians();
    }

    public void run() {
        for (int i = 0; i < iterations; i++) {
            doOneRun();
        }
    }

    public void doPrint() {
        System.out.println(className + "\n\n----------------------------\n\n");
        System.out.println("Medians(Wiederholunen: " + iterations + ", Größe: " + testSize + ")\n");
        System.out.println("Index:\t\t" + medianIndexedTime);
        System.out.println("Iterator:\t" + medianIteratorTime);
        System.out.println("Lambda:\t\t" + medianLambdaTime + "\n");
    }

    public int getRuns() {
        return runs;
    }

    public long getLastIteratorTime() {
        return lastIteratorTime;
    }

    public long getLastIndexedTime() {
        return lastIndexedTime;
    }

    public long getLastLambdaTime() {
        return lastLambdaTime;
    }

    public long getMedianIteratorTime() {
        return medianIteratorTime;
    }

    public long getMedianIndexedTime() {
        return medianIndexedTime;
    }

    public long getMedianLambdaTime() {
        return medianLambdaTime;
    }

    public String getClassName() {
        return className;
    }

}
