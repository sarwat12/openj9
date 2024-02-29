/*******************************************************************************
 * Copyright IBM Corp. and others 2019
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License 2.0 which accompanies this
 * distribution and is available at https://www.eclipse.org/legal/epl-2.0/
 * or the Apache License, Version 2.0 which accompanies this distribution and
 * is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * This Source Code may also be made available under the following
 * Secondary Licenses when the conditions for such availability set
 * forth in the Eclipse Public License, v. 2.0 are satisfied: GNU
 * General Public License, version 2 with the GNU Classpath
 * Exception [1] and GNU General Public License, version 2 with the
 * OpenJDK Assembly Exception [2].
 *
 * [1] https://www.gnu.org/software/classpath/license.html
 * [2] https://openjdk.org/legal/assembly-exception.html
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0 OR GPL-2.0-only WITH Classpath-exception-2.0 OR GPL-2.0-only WITH OpenJDK-assembly-exception-1.0
 *******************************************************************************/

package jit.test.recognizedMethod;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

public class TestJavaLangMath {

    /**
    * Tests the constant corner cases defined by the {@link Math.sqrt} method.
    * <p>
    * The JIT compiler will transform calls to {@link Math.sqrt} within this test
    * into the following tree sequence:
    *
    * <code>
    * dsqrt
    *   dconst <x>
    * </code>
    *
    * Subsequent tree simplification passes will attempt to reduce this constant
    * operation to a <code>dsqrt</code> IL by performing the square root at compile
    * time. The transformation will be performed when the function get executed 
    * twice, therefore, the "invocationCount=2" is needed. However we must ensure the
    * result of the square root done by the compiler at compile time will be exactly 
    * the same as the result had it been done by the Java runtime at runtime. This 
    * test validates the results are the same.
    */
    @Test(groups = {"level.sanity"}, invocationCount=2)
    public void test_java_lang_Math_sqrt() {
        AssertJUnit.assertTrue(Double.isNaN(Math.sqrt(Double.NEGATIVE_INFINITY)));
        AssertJUnit.assertTrue(Double.isNaN(Math.sqrt(-42.25d)));
        AssertJUnit.assertEquals(-0.0d, Math.sqrt(-0.0d));
        AssertJUnit.assertEquals(+0.0d, Math.sqrt(+0.0d));
        AssertJUnit.assertEquals(7.5d, Math.sqrt(56.25d));
        AssertJUnit.assertEquals(Double.POSITIVE_INFINITY, Math.sqrt(Double.POSITIVE_INFINITY));
        AssertJUnit.assertTrue(Double.isNaN(Math.sqrt(Double.NaN)));
    }

    /**
    * Tests the constant corner cases defined by the {@link Math.max} method, for float and double data types.
    */
    @Test(groups = {"level.sanity"}, invocationCount=2)
    public void test_java_lang_Math_max() {
        // Test Math.max for double type
        AssertJUnit.assertTrue(Double.isNaN(Math.max(Double.NaN, Double.NaN)));
        AssertJUnit.assertTrue(Double.isNaN(Math.max(Double.NaN, 0.0)));
        AssertJUnit.assertTrue(Double.isNaN(Math.max(0.0, Double.NaN)));
        AssertJUnit.assertTrue(Double.isNaN(Math.max(Double.NaN, -0.0)));
        AssertJUnit.assertTrue(Double.isNaN(Math.max(-0.0, Double.NaN)));
        AssertJUnit.assertEquals(0.0, Math.max(0.0, -0.0), 0.0);
        AssertJUnit.assertEquals(0.0, Math.max(-0.0, 0.0), 0.0);

         // Test Math.max for float type
         AssertJUnit.assertTrue(Float.isNaN(Math.max(Float.NaN, Float.NaN)));
         AssertJUnit.assertTrue(Float.isNaN(Math.max(Float.NaN, 0.0f)));
         AssertJUnit.assertTrue(Float.isNaN(Math.max(0.0f, Float.NaN)));
         AssertJUnit.assertTrue(Float.isNaN(Math.max(Float.NaN, -0.0f)));
         AssertJUnit.assertTrue(Float.isNaN(Math.max(-0.0f, Float.NaN)));
         AssertJUnit.assertEquals(0.0f, Math.max(0.0f, -0.0f), 0.0f);
         AssertJUnit.assertEquals(0.0f, Math.max(-0.0f, 0.0f), 0.0f);
    }

    /**
    * Tests the constant corner cases defined by the {@link Math.min} method, for float and double data types.
    */
    @Test(groups = {"level.sanity"}, invocationCount=2)
    public void test_java_lang_Math_min() {
        // Test Math.min for double type
        AssertJUnit.assertTrue(Double.isNaN(Math.min(Double.NaN, Double.NaN)));
        AssertJUnit.assertTrue(Double.isNaN(Math.min(Double.NaN, 0.0)));
        AssertJUnit.assertTrue(Double.isNaN(Math.min(0.0, Double.NaN)));
        AssertJUnit.assertTrue(Double.isNaN(Math.min(Double.NaN, -0.0)));
        AssertJUnit.assertTrue(Double.isNaN(Math.min(-0.0, Double.NaN)));
        AssertJUnit.assertEquals(-0.0, Math.min(0.0, -0.0), 0.0);
        AssertJUnit.assertEquals(-0.0, Math.min(-0.0, 0.0), 0.0);

        // Test Math.min for float type
        AssertJUnit.assertTrue(Float.isNaN(Math.min(Float.NaN, Float.NaN)));
        AssertJUnit.assertTrue(Float.isNaN(Math.min(Float.NaN, 0.0f)));
        AssertJUnit.assertTrue(Float.isNaN(Math.min(0.0f, Float.NaN)));
        AssertJUnit.assertTrue(Float.isNaN(Math.min(Float.NaN, -0.0f)));
        AssertJUnit.assertTrue(Float.isNaN(Math.min(-0.0f, Float.NaN)));
        AssertJUnit.assertEquals(-0.0f, Math.min(0.0f, -0.0f), 0.0f);
        AssertJUnit.assertEquals(-0.0f, Math.min(-0.0f, 0.0f), 0.0f);
    }
}
