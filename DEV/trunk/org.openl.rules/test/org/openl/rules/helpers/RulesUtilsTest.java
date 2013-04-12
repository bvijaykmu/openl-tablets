package org.openl.rules.helpers;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.openl.exception.OpenLRuntimeException;
import org.openl.meta.BigDecimalValue;
import org.openl.meta.BigIntegerValue;
import org.openl.meta.ByteValue;
import org.openl.meta.DoubleValue;
import org.openl.meta.FloatValue;
import org.openl.meta.IntValue;
import org.openl.meta.LongValue;
import org.openl.meta.ObjectValue;
import org.openl.meta.ShortValue;
import org.openl.meta.StringValue;
import org.openl.meta.number.NumberValue;
import org.openl.rules.TestHelper;
import org.junit.*;
import static org.junit.Assert.*;
import org.openl.rules.helpers.RulesUtils;
import org.openl.rules.search.SearchTableRow;
import org.openl.rules.testmethod.OpenLUserRuntimeException;

/**
 * Test to check that methods from {@link RulesUtils} and children of
 * {@link NumberValue} are visible and executed from excel.
 * 
 * @author DLiauchuk
 * 
 *         TODO: test all methods
 */
public class RulesUtilsTest {

    private static final String src = "test/rules/helpers/RulesUtilsTest.xlsx";

    private static TestInterf instance;
    private static String str;
    private static String nullStr = null;

    public interface TestInterf {
        String testMaxByte(java.lang.Byte[] obj);
        String testMaxShort(java.lang.Short[] obj);
        String testMaxInt(java.lang.Integer[] obj);
        String testMaxLong(java.lang.Long[] onj);
        String testMaxFloat(java.lang.Float[] obj);
        String testMaxByteType(byte[] obj);
        String testMaxShortType(short[] obj);
        String testMaxIntType(int[] obj);
        String testMaxLongType(long[] onj);
        String testMaxFloatType(float[] obj);
        String testMaxBigInteger(BigInteger[] obj);
        String testMaxBigDecimal(BigDecimal[] obj);
        
        String testMinByte(java.lang.Byte[] obj);
        String testMinShort(java.lang.Short[] obj);
        String testMinInt(java.lang.Integer[] obj);
        String testMinLong(java.lang.Long[] onj);
        String testMinFloat(java.lang.Float[] obj);
        String testMinByteType(byte[] obj);
        String testMinShortType(short[] obj);
        String testMinIntType(int[] obj);
        String testMinLongType(long[] onj);
        String testMinFloatType(float[] obj);
        String testMinBigInteger(BigInteger[] obj);
        String testMinBigDecimal(BigDecimal[] obj);
        
        BigInteger testSumBigInteger(BigInteger[] values);
        BigDecimal testSumBigDecimal(BigDecimal[] values);
        java.lang.Byte testSumByte(java.lang.Byte[] values);
        java.lang.Short testSumShort(java.lang.Short[] values);
        java.lang.Integer testSumInteger(java.lang.Integer[] values);
        java.lang.Long testSumLong(java.lang.Long[] values);
        java.lang.Float testSumFloat(java.lang.Float[] values);
        byte testSumByteType(byte[] values);
        short testSumShortType(short[] values);
        int testSumIntegerType(int[] values);
        long testSumLongType(long[] values);
        float testSumFloatType(float[] values);
        
        java.lang.Byte testAvgByte(java.lang.Byte[] values);
        java.lang.Short testAvgShort(java.lang.Short[] values);
        java.lang.Integer testAvgInteger(java.lang.Integer[] values);
        java.lang.Long testAvgLong(java.lang.Long[] values);
        java.lang.Float testAvgFloat(java.lang.Float[] values);
        java.lang.Double testAvgDouble(java.lang.Double[] values);
        byte testAvgByteType(byte[] values);
        short testAvgShortType(short[] values);
        int testAvgIntegerType(int[] values);
        long testAvgLongType(long[] values);
        float testAvgFloatType(float[] values);
        double testAvgDoubleType(double[] values);
        BigDecimalValue testAvgBigDecimal(BigDecimalValue[] values);
        BigIntegerValue testAvgBigInteger(BigIntegerValue[] values);
        
        LongValue testQuaotientByteValue(ByteValue number, ByteValue divisor);
        LongValue testQuaotientShortValue(ShortValue number, ShortValue divisor);
        LongValue testQuaotientIntegerValue(IntValue number, IntValue divisor);
        LongValue testQuaotientLongValue(LongValue number, LongValue divisor);
        LongValue testQuaotientFloatValue(FloatValue number, FloatValue divisor);
        LongValue testQuaotientDoubleValue(DoubleValue number, DoubleValue divisor);
        LongValue testQuaotientBigIntegerValue(BigIntegerValue number, BigIntegerValue divisor);
        LongValue testQuaotientBigDecimalValue(BigDecimalValue number, BigDecimalValue divisor);
        LongValue testQuaotientByte(Byte number, Byte divisor);
        LongValue testQuaotientShort(Short number, Short divisor);
        LongValue testQuaotientInteger(Integer number, Integer divisor);
        LongValue testQuaotientLong(Long number, Long divisor);
        LongValue testQuaotientFloat(Float number, Float divisor);
        LongValue testQuaotientDouble(Double number, Double divisor);
        LongValue testQuaotientBigInteger(BigInteger number, BigInteger divisor);
        LongValue testQuaotientBigDecimal(BigDecimal number, BigDecimal divisor);
        LongValue testQuaotientByteType(byte number, byte divisor);
        LongValue testQuaotientShortType(short number, short divisor);
        LongValue testQuaotientIntegerType(int number, int divisor);
        LongValue testQuaotientLongType(long number, long divisor);
        LongValue testQuaotientFloatType(float number, float divisor);
        LongValue testQuaotientDoubleType(double number, double divisor);
        
        ByteValue testMinByteValue(ByteValue[] byteValues);
        ShortValue testMinShortValue(ShortValue[] shortValues);
        IntValue testMinIntegerValue(IntValue[] intValues);
        LongValue testMinLongValue(LongValue[] longValues);
        FloatValue testMinFloatValue(FloatValue[] floatValues);
        DoubleValue testMinDoubleValue(DoubleValue[] doubleValues);
        BigIntegerValue testMinBigIntegerValue(BigIntegerValue[] bigIntegerValues);
        BigDecimalValue testMinBigDecimalValue(BigDecimalValue[] bigDecimalValues);
        ByteValue testMaxByteValue(ByteValue[] byteValues);
        ShortValue testMaxShortValue(ShortValue[] shortValues);
        IntValue testMaxIntegerValue(IntValue[] intValues);
        LongValue testMaxLongValue(LongValue[] longValues);
        FloatValue testMaxFloatValue(FloatValue[] floatValues);
        DoubleValue testMaxDoubleValue(DoubleValue[] doubleValues);
        BigIntegerValue testMaxBigIntegerValue(BigIntegerValue[] bigIntegerValues);
        BigDecimalValue testMaxBigDecimalValue(BigDecimalValue[] bigDecimalValues);
        
        ByteValue testModByteValue(ByteValue byteValue, ByteValue byteValue2);
        ShortValue testModShortValue(ShortValue shortValue, ShortValue shortValue2);
        IntValue testModIntegerValue(IntValue intValue, IntValue intValue2);
        LongValue testModLongValue(LongValue longValue, LongValue longValue2);
        FloatValue testModFloatValue(FloatValue floatValue, FloatValue floatValue2);
        BigDecimalValue testModBigDecimalValue(BigDecimalValue bigDecimalValue,
				BigDecimalValue bigDecimalValue2);
        BigIntegerValue testModBigIntegerValue(BigIntegerValue bigIntegerValue,
				BigIntegerValue bigIntegerValue2);
        Byte testModByte(Byte byte1, Byte byte2);
        Short testModShort(Short short1, Short short2);
        Integer testModInteger(Integer integer, Integer integer2);
        Long testModLong(Long long1, Long long2);
        Float testModFloat(Float float1, Float float2);
        BigDecimal testModBigDecimal(BigDecimal bigDecimal, BigDecimal bigDecimal2);
        BigInteger testModBigInteger(BigInteger valueOf, BigInteger valueOf2);
        byte testModByteType(byte b, byte c);
        short testModShortType(short s, short t);
        int testModIntegerType(int i, int j);
        long testModLongType(long l, long m);
        float testModFloatType(float f, float g);
		
        boolean checkOr();
        Byte[] testSliceByte(Byte[] byteValues, int startIndex);
        Byte[] testSliceByte(Byte[] bytes, int i, int j);
        Short[] testSliceShort(Short[] shorts, int i, int j);
		Short[] testSliceShort(Short[] shorts, int i);
		Integer[] testSliceInteger(Integer[] integers, int i);
		Integer[] testSliceInteger(Integer[] integers, int i, int j);
		Long[] testSliceLong(Long[] longs, int i);
		Long[] testSliceLong(Long[] longs, int i, int j);
		Float[] testSliceFloat(Float[] floats, int i);
		Float[] testSliceFloat(Float[] floats, int i, int j);
		Double[] testSliceDouble(Double[] doubles, int i, int j);
		Double[] testSliceDouble(Double[] doubles, int i);
		BigInteger[] testSliceBigInteger(BigInteger[] bigIntegers, int i);
		BigInteger[] testSliceBigInteger(BigInteger[] bigIntegers, int i, int j);
		BigDecimal[] testSliceBigDecimal(BigDecimal[] bigDecimals, int i);
		BigDecimal[] testSliceBigDecimal(BigDecimal[] bigDecimals, int i, int j);
		byte[] testSliceByteType(byte[] bs, int i);
		byte[] testSliceByteType(byte[] bs, int i, int j);
		short[] testSliceShortType(short[] s, int i);
		short[] testSliceShortType(short[] s, int i, int j);
		int[] testSliceIntegerType(int[] is, int i);
		int[] testSliceIntegerType(int[] is, int i, int j);
		long[] testSliceLongType(long[] ls, int i);
		long[] testSliceLongType(long[] ls, int i, int j);
		float[] testSliceFloatType(float[] fs, int i);
		float[] testSliceFloatType(float[] fs, int i, int j);
		double[] testSliceDoubleType(double[] ds, int i);
		double[] testSliceDoubleType(double[] ds, int i, int j);
		ObjectValue[] testSortObjectValue(ObjectValue[] strValueArray);
		Date[] testSortDate(Date[] nullDateArrayValue);
		StringValue[] testSortStringValue(StringValue[] strValueArray);
		String[] testSortString(String[] strValueArray);
		BigDecimalValue[] testSortBigDecimalValue(BigDecimalValue[] inputArray);
		BigIntegerValue[] testSortBigIntegerValue(BigIntegerValue[] inputArray);
		BigDecimal[] testSortBigDecimal(BigDecimal[] inputArray);
		BigInteger[] testSortBigInteger(BigInteger[] inputArray);
		DoubleValue[] testSortDoubleValue(DoubleValue[] inputArray);
		FloatValue[] testSortFloatValue(FloatValue[] inputArray);
		LongValue[] testSortLongValue(LongValue[] inputArray);
		IntValue[] testSortIntegerValue(IntValue[] inputArray);
		ShortValue[] testSortShortValue(ShortValue[] inputArray);
		ByteValue[] testSortByteValue(ByteValue[] inputArray);
		Double[] testSortDouble(Double[] inputArray);
		Float[] testSortFloat(Float[] inputArray);
		Long[] testSortLong(Long[] inputArray);
		Integer[] testSortInteger(Integer[] inputArray);
		Short[] testSortShort(Short[] inputArray);
		Byte[] testSortByte(Byte[] inputArray);
		double[] testSortDoubleType(double[] inputArray);
		float[] testSortFloatType(float[] inputArray);
		long[] testSortLongType(long[] inputArray);
		int[] testSortIntegerType(int[] inputArray);
		short[] testSortShortType(short[] inputArray);
		byte[] testSortByteType(byte[] inputArray);
		boolean testContainsObjectInObjectArr(Object[] searchIn,
				Object searchFor);
		boolean testContainsCharArrInCharArr(char[] searchIn, char[] searchFor);
		boolean testContainsBoolArrInBoolArr(boolean[] searchIn,
				boolean[] searchFor);
		boolean testContainsDoubleArrInDoubleArr(double[] searchIn,
				double[] searchFor);
		boolean testContainsFloatArrInFloatArr(float[] searchIn,
				float[] searchFor);
		boolean testContainsShortArrInShortArr(short[] searchIn,
				short[] searchFor);
		boolean testContainsByteArrInByteArr(byte[] searchIn, byte[] searchFor);
		boolean testContainsLongArrInLongArr(long[] searchIn, long[] searchFor);
		boolean testContainsIntegerArrInIntegerArr(int[] searchIn,
				int[] searchFor);
		boolean testContainsObjectArrInObjectArr(Object[] searchIn,
				Object[] searchFor);
		boolean testContainsBoolInBoolArr(boolean[] searchIn, boolean searchFor);
		boolean testContainsDoubleInDoubleArr(double[] searchIn,
				double searchFor);
		boolean testContainsFloatInFloatArr(float[] searchIn, float searchFor);
		boolean testContainsCharInCharArr(char[] searchIn, char searchFor);
		boolean testContainsShortInShortArr(short[] searchIn, short searchFor);
		boolean testContainsByteInByteArr(byte[] searchIn, byte searchFor);
		boolean testContainsLongInLongArr(long[] searchIn, long searchFor);
		boolean testContainsIntegerInIntegerArr(int[] searchIn, int searchFor);
		Object testIndexOfObject(Object[] objects, Object object);
		Object testIndexOfBool(boolean[] bs, boolean b);
		Object testIndexOfDouble(double[] ds, double d);
		Object testIndexOfFloat(float[] fs, float f);
		Object testIndexOfChar(char[] cs, char c);
		Object testIndexOfShort(short[] s, short t);
		Object testIndexOfByte(byte[] bs, byte b);
		Object testIndexOfLong(long[] ls, long l);
		Object testIndexOfInteger(int[] is, int i);
		boolean testNoNullsObject(Object[] objects);
		void testError(String string);
		void testErrorThrowable(Throwable ex);
		Object formatDouble(double d);
		Object formatDoubleWithFrm(double d, String string);
		Object[] testIntersectionStringArr(String[] searchIn, String[] searchFor);
		Object testAbsMonth(Date dateNow);
		Object testAbsQuarter(Date dateNow);
		Object testDiffDate(Date date1, Date date2);
		Object testDayOfMonth(Date date1);
		Date testFirstDayOfQuarter(int i);
		Object testLastDayOfQuarter(int i);
		Object testLastDayOfMonth(Date time);
		Object testGetMonth(Date time);
		int testMonthDiff(Date date1, Date date2);
		
		
    }

    @Before
    public void init() {
    	Locale locale = Locale.getDefault(); 
    	locale = Locale.US;
    	Locale.setDefault(locale);
    	
        if (instance == null) {
            File xlsFile = new File(src);
            TestHelper<TestInterf> testHelper;
            testHelper = new TestHelper<TestInterf>(xlsFile, TestInterf.class);
            str = "Testing string value";

            instance = testHelper.getInstance();
        }
    }

    @Test
    public void testByteMax() {
        assertEquals("res2", instance.testMaxByte(new java.lang.Byte[] { (byte) 127, (byte) -128 }));
    }
    
    @Test
    public void testShortMax(){
    	assertEquals("res1", instance.testMaxShort(new java.lang.Short[] { (short) 32767, (short) -32768 }));
    }
    
    @Test
    public void testIntMax(){
    	assertEquals("res1", instance.testMaxInt(new java.lang.Integer[] { (int) 2147483647, (int) -2147483648 }));
    }
    
    @Test
    public void testLongMax(){
    	Long a = new Long("9200000000000000000");
    	Long b = new Long("9100000000000000009");
    	    	assertEquals("res1", instance.testMaxLong(new java.lang.Long[] { a , b }));
    }
    
    @Test
    public void testFloatMax(){
    	Float a = new Float("3.40000000000000000000000000000000000000");
    	Float b = new Float("2.40000000000000000000000000000000000000");
    	assertEquals("res1", instance.testMaxFloat(new java.lang.Float[] { a, b }));
    }
    
    @Test
    public void testByteTypeMax() {
        assertEquals("res2", instance.testMaxByteType(new byte[] { (byte) 127, (byte) -128 }));
    }
    
    @Test
    public void testShortTypeMax(){
    	assertEquals("res1", instance.testMaxShortType(new short[] { (short) 32767, (short) -32768 }));
    }
    
    @Test
    public void testIntTypeMax(){
    	assertEquals("res1", instance.testMaxIntType(new int[] { (int) 2147483647, (int) -2147483648 }));
    }
    
    @Test
    public void testLongTypeMax(){
    	Long a = new Long("9200000000000000000");
    	Long b = new Long("9100000000000000009");
    	    	assertEquals("res1", instance.testMaxLongType(new long[] { a , b }));
    }
    
    @Test
    public void testFloatTypeMax(){
    	Float a = new Float("3.40000000000000000000000000000000000000");
    	Float b = new Float("2.40000000000000000000000000000000000000");
    	assertEquals("res1", instance.testMaxFloatType(new float[] { a, b }));
    }
    
    @Test
    public void testBigIntegerMax(){
    	assertEquals("res1", instance.testMaxBigInteger(new BigInteger[] { BigInteger.valueOf(0), BigInteger.valueOf(123) }));
    }
    
    @Test
    public void testBigDecimalMax(){
    	assertEquals("res1", instance.testMaxBigDecimal(new BigDecimal[] { BigDecimal.valueOf(0), BigDecimal.valueOf(123) }));
    }
    
    @Test
    public void testByteMin() {
        assertEquals("res1", instance.testMinByte(new java.lang.Byte[] { (byte) 127, (byte) -128 }));
    }
    
    @Test
    public void testShortMin(){
    	assertEquals("res2", instance.testMinShort(new java.lang.Short[] { (short) 32767, (short) -32768 }));
    }
    
    @Test
    public void testIntMin(){
    	assertEquals("res2", instance.testMinInt(new java.lang.Integer[] { (int) 2147483647, (int) -2147483648 }));
    }
    
    @Test
    public void testLongMin(){
    	Long a = new Long("9200000000000000000");
    	Long b = new Long("9100000000000000009");
    	    	assertEquals("res2", instance.testMinLong(new java.lang.Long[] { a , b }));
    }
    
    @Test
    public void testFloatMin(){
    	Float a = new Float("3.40000000000000000000000000000000000000");
    	Float b = new Float("2.40000000000000000000000000000000000000");
    	assertEquals("res2", instance.testMinFloat(new java.lang.Float[] { a, b }));
    }
    
    @Test
    public void testByteTypeMin() {
        assertEquals("res1", instance.testMinByteType(new byte[] { (byte) 127, (byte) -128 }));
    }
    
    @Test
    public void testShortTypeMin(){
    	assertEquals("res2", instance.testMinShortType(new short[] { (short) 32767, (short) -32768 }));
    }
    
    @Test
    public void testIntTypeMin(){
    	assertEquals("res2", instance.testMinIntType(new int[] { (int) 2147483647, (int) -2147483648 }));
    }
    
    @Test
    public void testLongTypeMin(){
    	Long a = new Long("9200000000000000000");
    	Long b = new Long("9100000000000000009");
    	    	assertEquals("res2", instance.testMinLongType(new long[] { a , b }));
    }
    
    @Test
    public void testFloatTypeMin(){
    	Float a = new Float("3.40000000000000000000000000000000000000");
    	Float b = new Float("2.40000000000000000000000000000000000000");
    	assertEquals("res2", instance.testMinFloatType(new float[] { a, b }));
    }
    
    @Test
    public void testBigIntegerMin(){
    	assertEquals("res2", instance.testMinBigInteger(new BigInteger[] { BigInteger.valueOf(0), BigInteger.valueOf(123) }));
    }
    
    @Test
    public void testBigDecimalMin(){
    	assertEquals("res2", instance.testMinBigDecimal(new BigDecimal[] { BigDecimal.valueOf(0), BigDecimal.valueOf(123) }));
    }
    
    
    @Test
    public void testByteSum(){
    	assertEquals(java.lang.Byte.valueOf((byte) 127),
                instance.testSumByte(new java.lang.Byte[] { java.lang.Byte.valueOf((byte) 0), java.lang.Byte.valueOf((byte) 101), java.lang.Byte.valueOf((byte) 26) }));
    }
    
    @Test
    public void testShortSum(){
    	assertEquals(java.lang.Short.valueOf((short) 30),
                instance.testSumShort(new java.lang.Short[] { java.lang.Short.valueOf((short) 10), java.lang.Short.valueOf((short) 5), java.lang.Short.valueOf((short) 15) }));
    }
    
    @Test
    public void testIntegerSum(){
    	assertEquals(java.lang.Integer.valueOf((java.lang.Integer) 30),
                instance.testSumInteger(new java.lang.Integer[] { java.lang.Integer.valueOf((java.lang.Integer) 10), java.lang.Integer.valueOf((java.lang.Integer) 5), java.lang.Integer.valueOf((java.lang.Integer) 15) }));
    }
    
    @Test
    public void testLongSum(){
    	assertEquals(java.lang.Long.valueOf((long) 30),
                instance.testSumLong(new java.lang.Long[] { java.lang.Long.valueOf((long) 10), java.lang.Long.valueOf((long) 5), java.lang.Long.valueOf((long) 15) }));
    }
    
    @Test
    public void testFloatSum(){
    	assertEquals(java.lang.Float.valueOf((float) 30.5),
                instance.testSumFloat(new java.lang.Float[] { java.lang.Float.valueOf((float) 10.1), java.lang.Float.valueOf((float) 5.3), java.lang.Float.valueOf((float) 15.1) }), 1e-15);
    }
    
    @Test
    public void testBigDecimalSum() {
        assertEquals(BigDecimal.valueOf(30),
            instance.testSumBigDecimal(new BigDecimal[] { BigDecimal.valueOf(10), BigDecimal.valueOf(5), BigDecimal.valueOf(15) }));
    }
    
    @Test
    public void testBigIntegerSum() {
        assertEquals(BigInteger.valueOf(30),
            instance.testSumBigInteger(new BigInteger[] { BigInteger.valueOf(10), BigInteger.valueOf(5), BigInteger.valueOf(15) }));
    }
    
    @Test
    public void testByteTypeSum(){
    	assertEquals(((byte) 127),
                instance.testSumByteType(new byte[] { (byte) 101, (byte) 0, (byte) 26 }));
    }
    
    @Test
    public void testShortTypeSum(){
    	assertEquals((short) 30,
                instance.testSumShortType(new short[] { (short) 10, (short) 5, (short) 15 }));
    }
    
    @Test
    public void testIntegerTypeSum(){
    	assertEquals(30,
                instance.testSumIntegerType(new int[] {  10, 5, 15 }));
    }
    
    @Test
    public void testLongTypeSum(){
    	assertEquals((long) 30,
                instance.testSumLongType(new long[] { (long) 10, (long) 5, (long) 15 }));
    }
    
    @Test
    public void testFloatTypeSum(){
    	assertEquals((float) 30.5,
                instance.testSumFloatType(new float[] { (float) 10.3, (float) 5.1, (float) 15.1 }), 1e-15);
    }
    
    
	@Test
	public void testByteTypeAvg() {
		assertEquals((byte) 12, instance.testAvgByteType(new byte[] { (byte) 10,
				(byte) 15, (byte) 13 }));
	}
	
	@Test
	public void testShortTypeAvg() {
		assertEquals((short) 12, instance.testAvgShortType(new short[] { (short) 10,
				(short) 15, (short) 13 }));
	}
    
	@Test
	public void testIntegerTypeAvg() {
		assertEquals(12, instance.testAvgIntegerType(new int[] { 10,
				15, 13 }));
	}
	
	@Test
	public void testLongTypeAvg() {
		assertEquals((long) 12, instance.testAvgLongType(new long[] { (long) 10,
				(long) 15, (long) 13 }));
	}
	
	@Test
	public void testFloatTypeAvg() {
		assertEquals((float) 12.666666984558105, instance.testAvgFloatType(new float[] { (float) 10,
				(float) 15, (float) 13 }), 1e-15);
	}
	
	@Test
	public void testDoubleTypeAvg() {
		assertEquals((double) 12.666666666666666, instance.testAvgDoubleType(new double[] { (double) 10,
				(double) 15, (double) 13 }), 1e-15);
	}
	
	@Test
	public void testByteAvg() {
		assertEquals(java.lang.Byte.valueOf((byte) 12), instance.testAvgByte(new java.lang.Byte[] { java.lang.Byte.valueOf((byte) 10),
				java.lang.Byte.valueOf((byte) 15), java.lang.Byte.valueOf((byte) 13) }));
	}
	
	@Test
	public void testShortAvg() {
		assertEquals(java.lang.Short.valueOf((short) 12), instance.testAvgShort(new java.lang.Short[] { java.lang.Short.valueOf((short) 10),
				java.lang.Short.valueOf((short) 15), java.lang.Short.valueOf((short) 13) }));
	}
    
	@Test
	public void testIntegerAvg() {
		assertEquals(java.lang.Integer.valueOf(12), instance.testAvgInteger(new java.lang.Integer[] { java.lang.Integer.valueOf(10),
				java.lang.Integer.valueOf(15), java.lang.Integer.valueOf(13) }));
	}
	
	@Test
	public void testLongAvg() {
		assertEquals(java.lang.Long.valueOf((long) 12), instance.testAvgLong(new java.lang.Long[] { java.lang.Long.valueOf((long) 10),
				java.lang.Long.valueOf((long) 15), java.lang.Long.valueOf((long) 13) }));
	}
	
	@Test
	public void testFloatAvg() {
		assertEquals(java.lang.Float.valueOf((float) 12.666667), instance.testAvgFloat(new java.lang.Float[] { java.lang.Float.valueOf((float) 10),
				java.lang.Float.valueOf((float) 15), java.lang.Float.valueOf((float) 13) }), 1e-15);
	}
	
	@Test
	public void testDoubleAvg() {
		assertEquals(java.lang.Double.valueOf((double) 12.666666666666666), instance.testAvgDouble(new java.lang.Double[] { java.lang.Double.valueOf((double) 10),
				java.lang.Double.valueOf((double) 15), java.lang.Double.valueOf((double) 13) }), 1e-15);
	}
	
    @Test
    public void testBigDecimalValueAvg() {
        assertEquals(new BigDecimalValue("12.66667"),
            instance.testAvgBigDecimal(new BigDecimalValue[] { new BigDecimalValue("10"),
                    new BigDecimalValue("15"),
                    new BigDecimalValue("13") }));

    }
    
    @Test
    public void testBigIntegerlValueAvg() {
        assertEquals(new BigIntegerValue("12"),
            instance.testAvgBigInteger(new BigIntegerValue[] { new BigIntegerValue("10"),
                    new BigIntegerValue("15"),
                    new BigIntegerValue("13") }));

    }
    
    
    @Test (expected = IllegalArgumentException.class)
    public void testSmallByteType() {
        byte [] array = {10, 32, 35, 25};
       assertTrue(RulesUtils.small(array, 1) == 10);
       assertTrue(RulesUtils.small(array, 2) == 25);
       assertTrue(RulesUtils.small(array, 3) == 32);
       assertTrue(RulesUtils.small(array, 4) == 35);
       
       RulesUtils.small(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testSmallShortType() {
        short [] array = {10, 32, 35, 25};
       assertTrue(RulesUtils.small(array, 1) == 10);
       assertTrue(RulesUtils.small(array, 2) == 25);
       assertTrue(RulesUtils.small(array, 3) == 32);
       assertTrue(RulesUtils.small(array, 4) == 35);
       
       RulesUtils.small(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testSmallIntegerType() {
        int [] array = {10, 32, 35, 25};
       assertTrue(RulesUtils.small(array, 1) == 10);
       assertTrue(RulesUtils.small(array, 2) == 25);
       assertTrue(RulesUtils.small(array, 3) == 32);
       assertTrue(RulesUtils.small(array, 4) == 35);
       
       RulesUtils.small(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testSmallLongType() {
        long [] array = {10, 32, 35, 25};
       assertTrue(RulesUtils.small(array, 1) == 10);
       assertTrue(RulesUtils.small(array, 2) == 25);
       assertTrue(RulesUtils.small(array, 3) == 32);
       assertTrue(RulesUtils.small(array, 4) == 35);
       
       RulesUtils.small(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testSmallFloatType() {
        float [] array = {(float) 10.1, (float) 32.2, (float) 35.4, (float) 25.5};
       assertTrue(RulesUtils.small(array, 1) == (float) 10.1);
       assertTrue(RulesUtils.small(array, 2) == (float) 25.5);
       assertTrue(RulesUtils.small(array, 3) == (float) 32.2);
       assertTrue(RulesUtils.small(array, 4) == (float) 35.4);
       
       RulesUtils.small(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testSmallDoubleType() {
        double [] array = {10.1, 32.2, 35.3, 25.4};
       assertTrue(RulesUtils.small(array, 1) == 10.1);
       assertTrue(RulesUtils.small(array, 2) == 25.4);
       assertTrue(RulesUtils.small(array, 3) == 32.2);
       assertTrue(RulesUtils.small(array, 4) == 35.3);
       
       RulesUtils.small(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testSmallBigDecimal() {
       BigDecimal [] array = {BigDecimal.valueOf(10), BigDecimal.valueOf(32), BigDecimal.valueOf(35), BigDecimal.valueOf(25)};
       assertTrue(RulesUtils.small(array, 1).equals(BigDecimal.valueOf(10)));
       assertTrue(RulesUtils.small(array, 2).equals(BigDecimal.valueOf(25)));
       assertTrue(RulesUtils.small(array, 3).equals(BigDecimal.valueOf(32)));
       assertTrue(RulesUtils.small(array, 4).equals(BigDecimal.valueOf(35)));
       
       RulesUtils.small(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testSmallBigInteger() {
    	BigInteger [] array = {BigInteger.valueOf(10), BigInteger.valueOf(32), BigInteger.valueOf(35), BigInteger.valueOf(25)};
       assertTrue(RulesUtils.small(array, 1).equals(BigInteger.valueOf(10)));
       assertTrue(RulesUtils.small(array, 2).equals(BigInteger.valueOf(25)));
       assertTrue(RulesUtils.small(array, 3).equals(BigInteger.valueOf(32)));
       assertTrue(RulesUtils.small(array, 4).equals(BigInteger.valueOf(35)));
       
       RulesUtils.small(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testSmallByte() {
        java.lang.Byte [] array = {10, 32, 35, 25};
       assertTrue(RulesUtils.small(array, 1) == java.lang.Byte.valueOf((byte) 10));
       assertTrue(RulesUtils.small(array, 2) == java.lang.Byte.valueOf((byte) 25));
       assertTrue(RulesUtils.small(array, 3) == java.lang.Byte.valueOf((byte) 32));
       assertTrue(RulesUtils.small(array, 4) == java.lang.Byte.valueOf((byte) 35));
       
       RulesUtils.small(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testSmallShort() {
        java.lang.Short [] array = {10, 32, 35, 25};
       assertTrue(RulesUtils.small(array, 1) == java.lang.Short.valueOf((short) 10));
       assertTrue(RulesUtils.small(array, 2) == java.lang.Short.valueOf((short) 25));
       assertTrue(RulesUtils.small(array, 3) == java.lang.Short.valueOf((short) 32));
       assertTrue(RulesUtils.small(array, 4) == java.lang.Short.valueOf((short) 35));
       
       RulesUtils.small(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testSmallInteger() {
        java.lang.Integer [] array = {10, 32, 35, 25};
       assertTrue(RulesUtils.small(array, 1) == java.lang.Integer.valueOf(10));
       assertTrue(RulesUtils.small(array, 2) == java.lang.Integer.valueOf(25));
       assertTrue(RulesUtils.small(array, 3) == java.lang.Integer.valueOf(32));
       assertTrue(RulesUtils.small(array, 4) == java.lang.Integer.valueOf(35));
       
       RulesUtils.small(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testSmallLong() {
        java.lang.Long [] array = {java.lang.Long.valueOf(10), java.lang.Long.valueOf(32), java.lang.Long.valueOf(35), java.lang.Long.valueOf(25)};
       assertTrue(RulesUtils.small(array, 1) == java.lang.Long.valueOf(10));
       assertTrue(RulesUtils.small(array, 2) == java.lang.Long.valueOf(25));
       assertTrue(RulesUtils.small(array, 3) == java.lang.Long.valueOf(32));
       assertTrue(RulesUtils.small(array, 4) == java.lang.Long.valueOf(35));
       
       RulesUtils.small(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testSmallFloat() {
        java.lang.Float [] array = {java.lang.Float.valueOf( (float) 10.4), java.lang.Float.valueOf((float) 32.1), java.lang.Float.valueOf((float) 35.3), java.lang.Float.valueOf((float) 25.7)};
       assertTrue(RulesUtils.small(array, 1).equals(java.lang.Float.valueOf((float) 10.4)));
       assertTrue(RulesUtils.small(array, 2).equals(java.lang.Float.valueOf((float) 25.7)));
       assertTrue(RulesUtils.small(array, 3).equals(java.lang.Float.valueOf((float) 32.1)));
       assertTrue(RulesUtils.small(array, 4).equals(java.lang.Float.valueOf((float) 35.3)));
       
       RulesUtils.small(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testSmallDouble() {
    	java.lang.Double[] array = {java.lang.Double.valueOf(10.4), java.lang.Double.valueOf(32.2), java.lang.Double.valueOf(35.6), java.lang.Double.valueOf(25.2)};
       assertTrue(RulesUtils.small(array, 1).equals(java.lang.Double.valueOf(10.4)));
       assertTrue(RulesUtils.small(array, 2).equals(java.lang.Double.valueOf(25.2)));
       assertTrue(RulesUtils.small(array, 3).equals(java.lang.Double.valueOf(32.2)));
       assertTrue(RulesUtils.small(array, 4).equals(java.lang.Double.valueOf(35.6)));
       
       RulesUtils.small(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testBigByteType() {
        byte [] array = {10, 32, 35, 25};
       assertTrue(RulesUtils.big(array, 4) == 10);
       assertTrue(RulesUtils.big(array, 3) == 25);
       assertTrue(RulesUtils.big(array, 2) == 32);
       assertTrue(RulesUtils.big(array, 1) == 35);
       
       RulesUtils.big(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testBigShortType() {
        short [] array = {10, 32, 35, 25};
       assertTrue(RulesUtils.big(array, 4) == 10);
       assertTrue(RulesUtils.big(array, 3) == 25);
       assertTrue(RulesUtils.big(array, 2) == 32);
       assertTrue(RulesUtils.big(array, 1) == 35);
       
       RulesUtils.big(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testBigIntegerType() {
        int [] array = {10, 32, 35, 25};
       assertTrue(RulesUtils.big(array, 4) == 10);
       assertTrue(RulesUtils.big(array, 3) == 25);
       assertTrue(RulesUtils.big(array, 2) == 32);
       assertTrue(RulesUtils.big(array, 1) == 35);
       
       RulesUtils.big(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testBigLongType() {
        long [] array = {10, 32, 35, 25};
       assertTrue(RulesUtils.big(array, 4) == 10);
       assertTrue(RulesUtils.big(array, 3) == 25);
       assertTrue(RulesUtils.big(array, 2) == 32);
       assertTrue(RulesUtils.big(array, 1) == 35);
       
       RulesUtils.big(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testBigFloatType() {
        float [] array = {(float) 10.1, (float) 32.2, (float) 35.4, (float) 25.5};
       assertTrue(RulesUtils.big(array, 4) == (float) 10.1);
       assertTrue(RulesUtils.big(array, 3) == (float) 25.5);
       assertTrue(RulesUtils.big(array, 2) == (float) 32.2);
       assertTrue(RulesUtils.big(array, 1) == (float) 35.4);
       
       RulesUtils.big(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testBigDoubleType() {
        double [] array = {10.1, 32.2, 35.3, 25.4};
       assertTrue(RulesUtils.big(array, 4) == 10.1);
       assertTrue(RulesUtils.big(array, 3) == 25.4);
       assertTrue(RulesUtils.big(array, 2) == 32.2);
       assertTrue(RulesUtils.big(array, 1) == 35.3);
       
       RulesUtils.big(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testBigBigDecimal() {
       BigDecimal [] array = {BigDecimal.valueOf(10), BigDecimal.valueOf(32), BigDecimal.valueOf(35), BigDecimal.valueOf(25)};
       assertTrue(RulesUtils.big(array, 4).equals(BigDecimal.valueOf(10)));
       assertTrue(RulesUtils.big(array, 3).equals(BigDecimal.valueOf(25)));
       assertTrue(RulesUtils.big(array, 2).equals(BigDecimal.valueOf(32)));
       assertTrue(RulesUtils.big(array, 1).equals(BigDecimal.valueOf(35)));
       
       RulesUtils.big(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testBigBigInteger() {
    	BigInteger [] array = {BigInteger.valueOf(10), BigInteger.valueOf(32), BigInteger.valueOf(35), BigInteger.valueOf(25)};
       assertTrue(RulesUtils.big(array, 4).equals(BigInteger.valueOf(10)));
       assertTrue(RulesUtils.big(array, 3).equals(BigInteger.valueOf(25)));
       assertTrue(RulesUtils.big(array, 2).equals(BigInteger.valueOf(32)));
       assertTrue(RulesUtils.big(array, 1).equals(BigInteger.valueOf(35)));
       
       RulesUtils.big(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testBigByte() {
        java.lang.Byte [] array = {10, 32, 35, 25};
       assertTrue(RulesUtils.big(array, 4) == java.lang.Byte.valueOf((byte) 10));
       assertTrue(RulesUtils.big(array, 3) == java.lang.Byte.valueOf((byte) 25));
       assertTrue(RulesUtils.big(array, 2) == java.lang.Byte.valueOf((byte) 32));
       assertTrue(RulesUtils.big(array, 1) == java.lang.Byte.valueOf((byte) 35));
       
       RulesUtils.big(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testBigShort() {
        java.lang.Short [] array = {10, 32, 35, 25};
       assertTrue(RulesUtils.big(array, 4) == java.lang.Short.valueOf((short) 10));
       assertTrue(RulesUtils.big(array, 3) == java.lang.Short.valueOf((short) 25));
       assertTrue(RulesUtils.big(array, 2) == java.lang.Short.valueOf((short) 32));
       assertTrue(RulesUtils.big(array, 1) == java.lang.Short.valueOf((short) 35));
       
       RulesUtils.big(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testBigInteger() {
        java.lang.Integer [] array = {10, 32, 35, 25};
       assertTrue(RulesUtils.big(array, 4) == java.lang.Integer.valueOf(10));
       assertTrue(RulesUtils.big(array, 3) == java.lang.Integer.valueOf(25));
       assertTrue(RulesUtils.big(array, 2) == java.lang.Integer.valueOf(32));
       assertTrue(RulesUtils.big(array, 1) == java.lang.Integer.valueOf(35));
       
       RulesUtils.big(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testBigLong() {
        java.lang.Long [] array = {java.lang.Long.valueOf(10), java.lang.Long.valueOf(32), java.lang.Long.valueOf(35), java.lang.Long.valueOf(25)};
       assertTrue(RulesUtils.big(array, 4) == java.lang.Long.valueOf(10));
       assertTrue(RulesUtils.big(array, 3) == java.lang.Long.valueOf(25));
       assertTrue(RulesUtils.big(array, 2) == java.lang.Long.valueOf(32));
       assertTrue(RulesUtils.big(array, 1) == java.lang.Long.valueOf(35));
       
       RulesUtils.big(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testBigFloat() {
        java.lang.Float [] array = {java.lang.Float.valueOf( (float) 10.4), java.lang.Float.valueOf((float) 32.1), java.lang.Float.valueOf((float) 35.3), java.lang.Float.valueOf((float) 25.7)};
       assertTrue(RulesUtils.big(array, 4).equals(java.lang.Float.valueOf((float) 10.4)));
       assertTrue(RulesUtils.big(array, 3).equals(java.lang.Float.valueOf((float) 25.7)));
       assertTrue(RulesUtils.big(array, 2).equals(java.lang.Float.valueOf((float) 32.1)));
       assertTrue(RulesUtils.big(array, 1).equals(java.lang.Float.valueOf((float) 35.3)));
       
       RulesUtils.big(array, 0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testBigDouble() {
    	java.lang.Double[] array = {java.lang.Double.valueOf(10.4), java.lang.Double.valueOf(32.2), java.lang.Double.valueOf(35.6), java.lang.Double.valueOf(25.2)};
       assertTrue(RulesUtils.big(array, 4).equals(java.lang.Double.valueOf(10.4)));
       assertTrue(RulesUtils.big(array, 3).equals(java.lang.Double.valueOf(25.2)));
       assertTrue(RulesUtils.big(array, 2).equals(java.lang.Double.valueOf(32.2)));
       assertTrue(RulesUtils.big(array, 1).equals(java.lang.Double.valueOf(35.6)));
       
       RulesUtils.big(array, 0);
    }
        
    @Test
    public void testByteValueQuaotient() {
        assertEquals(new LongValue(2), instance.testQuaotientByteValue(new ByteValue((byte) 25), new ByteValue((byte) 12)));
    }
    
    @Test
    public void testShortValueQuaotient() {
        assertEquals(new LongValue(2), instance.testQuaotientShortValue(new ShortValue((short) 25), new ShortValue((short) 12)));
    }
    
    @Test
    public void testIntegerValueQuaotient() {
        assertEquals(new LongValue(2), instance.testQuaotientIntegerValue(new IntValue(25), new IntValue(12)));
    }
    
    @Test
    public void testLongValueQuaotient() {
        assertEquals(new LongValue(2), instance.testQuaotientLongValue(new LongValue(25), new LongValue((long) 12)));
    }
    
    @Test
    public void testFloatValueQuaotient() {
        assertEquals(new LongValue(2), instance.testQuaotientFloatValue(new FloatValue((float) 25.4), new FloatValue((float) 12.2)));
    }
    
    @Test
    public void testDoubleValueQuaotient() {
        assertEquals(new LongValue(2), instance.testQuaotientDoubleValue(new DoubleValue(25.4), new DoubleValue(12.2)));
    }
    
    @Test
    public void testBigIntegerValueQuaotient() {
        assertEquals(new LongValue(2), instance.testQuaotientBigIntegerValue(new BigIntegerValue(BigInteger.valueOf(25)), new BigIntegerValue(BigInteger.valueOf(12))));
    }
    
    @Test
    public void testBigDecimalValueQuaotient() {
        assertEquals(new LongValue(2), instance.testQuaotientBigDecimalValue(new BigDecimalValue(BigDecimal.valueOf(25.4)), new BigDecimalValue(BigDecimal.valueOf(12.2))));
    }
    
    @Test
    public void testByteTypeQuaotient() {
        assertEquals(new LongValue(2), instance.testQuaotientByteType((byte) 25, (byte) 12));
    }
    
    @Test
    public void testShortTypeQuaotient() {
        assertEquals(new LongValue(2), instance.testQuaotientShortType((short) 25, (short) 12));
    }
    
    @Test
    public void testIntegerTypeQuaotient() {
        assertEquals(new LongValue(2), instance.testQuaotientIntegerType( 25, 12));
    }
    
    @Test
    public void testLongTypeQuaotient() {
        assertEquals(new LongValue(2), instance.testQuaotientLongType((long) 25, (long) 12));
    }
    
    @Test
    public void testFloatTypeQuaotient() {
        assertEquals(new LongValue(2), instance.testQuaotientFloatType((float) 25.5, (float) 12.2));
    }
    
    @Test
    public void testDoubleTypeQuaotient() {
        assertEquals(new LongValue(2), instance.testQuaotientDoubleType( 25.4, 12.2));
    }
    
    @Test
    public void testBigIntegerQuaotient() {
        assertEquals(new LongValue(2), instance.testQuaotientBigInteger(BigInteger.valueOf(25), BigInteger.valueOf(12)));
    }
    
    @Test
    public void testBigDecimalQuaotient() {
        assertEquals(new LongValue(2), instance.testQuaotientBigDecimal( BigDecimal.valueOf(25.4), BigDecimal.valueOf(12.2)));
    }
    
    @Test
    public void testByteQuaotient() {
        assertEquals(new LongValue(2), instance.testQuaotientByte( Byte.valueOf((byte) 25), Byte.valueOf((byte) 12)));
    }
    
    @Test
    public void testShortQuaotient() {
        assertEquals(new LongValue(2), instance.testQuaotientShort( Short.valueOf((short) 25), Short.valueOf((short) 12)));
    }
    
    @Test
    public void testIntegerQuaotient() {
        assertEquals(new LongValue(2), instance.testQuaotientInteger( Integer.valueOf(25), Integer.valueOf(12)));
    }
    
    @Test
    public void testLongQuaotient() {
        assertEquals(new LongValue(2), instance.testQuaotientLong( Long.valueOf((long) 25), Long.valueOf((long) 12)));
    }
    
    @Test
    public void testFloatQuaotient() {
        assertEquals(new LongValue(2), instance.testQuaotientFloat( Float.valueOf((float) 25.4), Float.valueOf((float) 12.2)));
    }
    
    @Test
    public void testDoubleQuaotient() {
        assertEquals(new LongValue(2), instance.testQuaotientDouble( Double.valueOf(25.4), Double.valueOf(12.2)));
    }

    @Test
    public void testByteValueMin() {
        assertEquals(new ByteValue((byte) 1),
            instance.testMinByteValue(new ByteValue[] { new ByteValue("10"), new ByteValue("15"), new ByteValue("1") }));
    }
    
    @Test
    public void testShortValueMin() {
        assertEquals(new ShortValue((short) 1),
            instance.testMinShortValue(new ShortValue[] { new ShortValue("10"), new ShortValue("15"), new ShortValue("1") }));
    }
    
    @Test
    public void testIntegerValueMin() {
        assertEquals(new IntValue(1),
            instance.testMinIntegerValue(new IntValue[] { new IntValue("10"), new IntValue("15"), new IntValue("1") }));
    }
    
    @Test
    public void testLongValueMin() {
        assertEquals(new LongValue((long) 1),
            instance.testMinLongValue(new LongValue[] { new LongValue("10"), new LongValue("15"), new LongValue("1") }));
    }
    
    @Test
    public void testFloatValueMin() {
        assertEquals(new FloatValue((float) 1.1),
            instance.testMinFloatValue(new FloatValue[] { new FloatValue("10.3"), new FloatValue("15.7"), new FloatValue("1.1") }));
    }
    
    @Test
    public void testDoubleValueMin() {
        assertEquals(new DoubleValue(1.1),
            instance.testMinDoubleValue(new DoubleValue[] { new DoubleValue("10.5"), new DoubleValue("15.7"), new DoubleValue("1.1") }));
    }
    
    @Test
    public void testBigIntegerValueMin() {
        assertEquals(new BigIntegerValue("1"),
            instance.testMinBigIntegerValue(new BigIntegerValue[] { new BigIntegerValue("10"), new BigIntegerValue("15"), new BigIntegerValue("1") }));
    }
    
    @Test
    public void testBigDecimalValueMin() {
        assertEquals(new BigDecimalValue("1.1"),
            instance.testMinBigDecimalValue(new BigDecimalValue[] { new BigDecimalValue("10.1"), new BigDecimalValue("15.1"), new BigDecimalValue("1.1") }));
    }
    
    @Test
    public void testByteValueMax() {
        assertEquals(new ByteValue((byte) 15),
            instance.testMaxByteValue(new ByteValue[] { new ByteValue("10"), new ByteValue("15"), new ByteValue("1") }));
    }
    
    @Test
    public void testShortValueMax() {
        assertEquals(new ShortValue((short) 15),
            instance.testMaxShortValue(new ShortValue[] { new ShortValue("10"), new ShortValue("15"), new ShortValue("1") }));
    }
    
    @Test
    public void testIntegerValueMax() {
        assertEquals(new IntValue(15),
            instance.testMaxIntegerValue(new IntValue[] { new IntValue("10"), new IntValue("15"), new IntValue("1") }));
    }
    
    @Test
    public void testLongValueMax() {
        assertEquals(new LongValue((long) 15),
            instance.testMaxLongValue(new LongValue[] { new LongValue("10"), new LongValue("15"), new LongValue("1") }));
    }
    
    @Test
    public void testFloatValueMax() {
        assertEquals(new FloatValue((float) 15.7),
            instance.testMaxFloatValue(new FloatValue[] { new FloatValue("10.3"), new FloatValue("15.7"), new FloatValue("1.1") }));
    }
    
    @Test
    public void testDoubleValueMax() {
        assertEquals(new DoubleValue(15.7),
            instance.testMaxDoubleValue(new DoubleValue[] { new DoubleValue("10.5"), new DoubleValue("15.7"), new DoubleValue("1.1") }));
    }
    
    @Test
    public void testBigIntegerValueMax() {
        assertEquals(new BigIntegerValue("15"),
            instance.testMaxBigIntegerValue(new BigIntegerValue[] { new BigIntegerValue("10"), new BigIntegerValue("15"), new BigIntegerValue("1") }));
    }
    
    @Test
    public void testBigDecimalValueMax() {
        assertEquals(new BigDecimalValue("15.1"),
            instance.testMaxBigDecimalValue(new BigDecimalValue[] { new BigDecimalValue("10.1"), new BigDecimalValue("15.1"), new BigDecimalValue("1.1") }));
    }
    
    
    @Test
    public void testByteValueMod(){
    	assertEquals(new ByteValue((byte) 1), instance.testModByteValue(new ByteValue((byte) 10), new ByteValue((byte) 3)));
    }
    
    @Test
    public void testShortValueMod(){
    	assertEquals(new ShortValue((short) 1), instance.testModShortValue(new ShortValue((short) 10), new ShortValue((short) 3)));
    }
    
    @Test
    public void testIntegerValueMod(){
    	assertEquals(new IntValue(1), instance.testModIntegerValue(new IntValue(10), new IntValue(3)));
    }
    
    @Test
    public void testLongValueMod(){
    	assertEquals(new LongValue((long) 1), instance.testModLongValue(new LongValue((long) 10), new LongValue((long) 3)));
    }
    
    @Test
    public void testFloatValueMod(){
    	assertEquals(new FloatValue((float) 0.5), instance.testModFloatValue(new FloatValue((float) 10.1), new FloatValue((float) 3.2)));
    }
    
    @Test
    public void testBigDecimalValueMod(){
    	assertEquals(new BigDecimalValue(BigDecimal.valueOf(0.5)), instance.testModBigDecimalValue(new BigDecimalValue(BigDecimal.valueOf(10.1)), new BigDecimalValue(BigDecimal.valueOf(3.2))));
    }
    
    @Test
    public void testBigIntegerValueMod(){
    	assertEquals(new BigIntegerValue(BigInteger.valueOf(1)), instance.testModBigIntegerValue(new BigIntegerValue(BigInteger.valueOf(10)), new BigIntegerValue(BigInteger.valueOf(3))));
    }
    
    @Test
    public void testByteMod(){
    	assertEquals(new Byte((byte) 1), instance.testModByte(new Byte((byte) 10), new Byte((byte) 3)));
    }
    
    @Test
    public void testShortMod(){
    	assertEquals(new Short((short) 1), instance.testModShort(new Short((short) 10), new Short((short) 3)));
    }
    
    @Test
    public void testIntegerMod(){
    	assertEquals(new Integer(1), instance.testModInteger(new Integer(10), new Integer(3)));
    }
    
    @Test
    public void testLongMod(){
    	assertEquals(new Long((long) 1), instance.testModLong(new Long((long) 10), new Long((long) 3)));
    }
    
    @Test
    public void testFloatMod(){
    	assertEquals(new Float((float) 0.5), instance.testModFloat(new Float((float) 10.1), new Float((float) 3.2)));
    }
    
    @Test
    public void testBigDecimalMod(){
    	assertEquals(new BigDecimal(0.5).setScale(3, RoundingMode.HALF_UP),  instance.testModBigDecimal(new BigDecimal(10.1), new BigDecimal(3.2)).setScale(3, RoundingMode.HALF_UP));
    }
    
    @Test
    public void testBigIntegerMod(){
    	assertEquals(BigInteger.valueOf(1), instance.testModBigInteger(BigInteger.valueOf(10), BigInteger.valueOf(3)));
    }
    
    @Test
    public void testByteTypeMod(){
    	assertEquals((byte) 1, instance.testModByteType((byte) 10, (byte) 3));
    }
    
    @Test
    public void testShortTypeMod(){
    	assertEquals((short) 1, instance.testModShortType((short) 10, (short) 3));
    }
    
    @Test
    public void testIntegerTypeMod(){
    	assertEquals(1, instance.testModIntegerType(10, 3));
    }
    
    @Test
    public void testLongModType(){
    	assertEquals((long) 1, instance.testModLongType((long) 10, (long) 3));
    }
    
    @Test
    public void testFloatModType(){
    	assertEquals((float) 0.5, instance.testModFloatType((float) 10.1, (float) 3.2), 1e-15);
    }
    
	@Test
	public void testByteSlice() {
		assertArrayEquals(
				new Byte[] { new Byte((byte) 3), new Byte((byte) 4), new Byte((byte) 5), new Byte((byte) 6), new Byte((byte) 7) },
				instance.testSliceByte(new Byte[] { new Byte((byte) 1), new Byte((byte) 2),
						new Byte((byte) 3), new Byte((byte) 4), new Byte((byte) 5), new Byte((byte) 6), new Byte((byte) 7) }, 2));
	}

	@Test
	public void testByteSliceEndIndex() {
		assertArrayEquals(new Byte[] { new Byte((byte) 3), new Byte((byte) 4), new Byte((byte) 5) },
				instance.testSliceByte(new Byte[] { new Byte((byte) 1), new Byte((byte) 2),
						new Byte((byte) 3), new Byte((byte) 4), new Byte((byte) 5), new Byte((byte) 6), new Byte((byte) 7) }, 2,
						5));
	}
	
	@Test
	public void testShortSlice() {
		assertArrayEquals(
				new Short[] { new Short((short) 3), new Short((short) 4), new Short((short) 5), new Short((short) 6), new Short((short) 7) },
				instance.testSliceShort(new Short[] { new Short((short) 1), new Short((short) 2),
						new Short((short) 3), new Short((short) 4), new Short((short) 5), new Short((short) 6), new Short((short) 7) }, 2));
	}

	@Test
	public void testShortSliceEndIndex() {
		assertArrayEquals(new Short[] { new Short((short) 3), new Short((short) 4), new Short((short) 5) },
				instance.testSliceShort(new Short[] { new Short((short) 1), new Short((short) 2),
						new Short((short) 3), new Short((short) 4), new Short((short) 5), new Short((short) 6), new Short((short) 7) }, 2,
						5));
	}
	
	@Test
	public void testIntegerSlice() {
		assertArrayEquals(
				new Integer[] { new Integer(3), new Integer(4), new Integer(5), new Integer(6), new Integer(7) },
				instance.testSliceInteger(new Integer[] { new Integer(1), new Integer(2),
						new Integer(3), new Integer(4), new Integer(5), new Integer(6), new Integer(7) }, 2));
	}

	@Test
	public void testIntegerSliceEndIndex() {
		assertArrayEquals(new Integer[] { new Integer(3), new Integer(4), new Integer(5) },
				instance.testSliceInteger(new Integer[] { new Integer(1), new Integer(2),
						new Integer(3), new Integer(4), new Integer(5), new Integer(6), new Integer(7) }, 2,
						5));
	}
	
	@Test
	public void testLongSlice() {
		assertArrayEquals(
				new Long[] { new Long(3), new Long(4), new Long(5), new Long(6), new Long(7) },
				instance.testSliceLong(new Long[] { new Long(1), new Long(2),
						new Long(3), new Long(4), new Long(5), new Long(6), new Long(7) }, 2));
	}

	@Test
	public void testLongSliceEndIndex() {
		assertArrayEquals(new Long[] { new Long(3), new Long(4), new Long(5) },
				instance.testSliceLong(new Long[] { new Long(1), new Long(2),
						new Long(3), new Long(4), new Long(5), new Long(6), new Long(7) }, 2,
						5));
	}
	
	@Test
	public void testFloatSlice() {
		assertArrayEquals(
				new Float[] { new Float(3), new Float(4), new Float(5), new Float(6), new Float(7) },
				instance.testSliceFloat(new Float[] { new Float(1), new Float(2),
						new Float(3), new Float(4), new Float(5), new Float(6), new Float(7) }, 2));
	}

	@Test
	public void testFloatSliceEndIndex() {
		assertArrayEquals(new Float[] { new Float(3.3), new Float(4.4), new Float(5.5) },
				instance.testSliceFloat(new Float[] { new Float(1.1), new Float(2.2),
						new Float(3.3), new Float(4.4), new Float(5.5), new Float(6.6), new Float(7.7) }, 2,
						5));
	}
	
	@Test
	public void testDoubleSlice() {
		assertArrayEquals(
				new Double[] { new Double(3.3), new Double(4.4), new Double(5.5), new Double(6.6), new Double(7.7) },
				instance.testSliceDouble(new Double[] { new Double(1.1), new Double(2.2),
						new Double(3.3), new Double(4.4), new Double(5.5), new Double(6.6), new Double(7.7) }, 2));
	}

	@Test
	public void testDoubleSliceEndIndex() {
		assertArrayEquals(new Double[] { new Double(3), new Double(4), new Double(5) },
				instance.testSliceDouble(new Double[] { new Double(1), new Double(2),
						new Double(3), new Double(4), new Double(5), new Double(6), new Double(7) }, 2,
						5));
	}
	
	@Test
	public void testBigIntegerSlice() {
		assertArrayEquals(
				new BigInteger[] { BigInteger.valueOf(3), BigInteger.valueOf(4), BigInteger.valueOf(5), BigInteger.valueOf(6), BigInteger.valueOf(7) },
				instance.testSliceBigInteger(new BigInteger[] { BigInteger.valueOf(1), BigInteger.valueOf(2),
						BigInteger.valueOf(3), BigInteger.valueOf(4), BigInteger.valueOf(5), BigInteger.valueOf(6), BigInteger.valueOf(7) }, 2));
	}

	@Test
	public void testBigIntegerSliceEndIndex() {
		assertArrayEquals(new BigInteger[] { BigInteger.valueOf(3), BigInteger.valueOf(4), BigInteger.valueOf(5) },
				instance.testSliceBigInteger(new BigInteger[] { BigInteger.valueOf(1), BigInteger.valueOf(2),
						BigInteger.valueOf(3), BigInteger.valueOf(4), BigInteger.valueOf(5), BigInteger.valueOf(6), BigInteger.valueOf(7) }, 2,
						5));
	}
	
	@Test
	public void testBigDecimalSlice() {
		assertArrayEquals(
				new BigDecimal[] { BigDecimal.valueOf(3.3), BigDecimal.valueOf(4.4), BigDecimal.valueOf(5.5), BigDecimal.valueOf(6.6), BigDecimal.valueOf(7.7) },
				instance.testSliceBigDecimal(new BigDecimal[] { BigDecimal.valueOf(1.1), BigDecimal.valueOf(2.2),
						BigDecimal.valueOf(3.3), BigDecimal.valueOf(4.4), BigDecimal.valueOf(5.5), BigDecimal.valueOf(6.6), BigDecimal.valueOf(7.7) }, 2));
	}

	@Test
	public void testBigDecimalSliceEndIndex() {
		assertArrayEquals(new BigDecimal[] { BigDecimal.valueOf(3.3), BigDecimal.valueOf(4.4), BigDecimal.valueOf(5.5) },
				instance.testSliceBigDecimal(new BigDecimal[] { BigDecimal.valueOf(1.1), BigDecimal.valueOf(2.2),
						BigDecimal.valueOf(3.3), BigDecimal.valueOf(4.4), BigDecimal.valueOf(5.5), BigDecimal.valueOf(6.6), BigDecimal.valueOf(7.7) }, 2,
						5));
	}
    
	
	@Test
	public void testByteTypeSlice() {
		assertArrayEquals(
				new byte[] { (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7 },
				instance.testSliceByteType(new byte[] { (byte) 1, (byte) 2,
						(byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7 }, 2));
	}

	@Test
	public void testByteTypeSliceEndIndex() {
		assertArrayEquals(new byte[] { (byte) 3, (byte) 4, (byte) 5 },
				instance.testSliceByteType(new byte[] { (byte) 1, (byte) 2,
						(byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7 }, 2,
						5));
	}

	@Test
	public void testShortTypeSlice() {
		assertArrayEquals(new short[] { (short) 3, (short) 4, (short) 5,
				(short) 6, (short) 7 }, instance.testSliceShortType(
				new short[] { (short) 1, (short) 2, (short) 3, (short) 4,
						(short) 5, (short) 6, (short) 7 }, 2));
	}

	@Test
	public void testShortTypeSliceEndIndex() {
		assertArrayEquals(new short[] { (short) 3, (short) 4, (short) 5 },
				instance.testSliceShortType(
						new short[] { (short) 1, (short) 2, (short) 3,
								(short) 4, (short) 5, (short) 6, (short) 7 },
						2, 5));
	}

	@Test
	public void testIntegerTypeSlice() {
		assertArrayEquals(new int[] { 3, 4, 5, 6, 7 },
				instance.testSliceIntegerType(
						new int[] { 1, 2, 3, 4, 5, 6, 7 }, 2));
	}

	@Test
	public void testIntegerTypeSliceEndIndex() {
		assertArrayEquals(new int[] { 3, 4, 5 }, instance.testSliceIntegerType(
				new int[] { 1, 2, 3, 4, 5, 6, 7 }, 2, 5));
	}

	@Test
	public void testLongTypeSlice() {
		assertArrayEquals(
				new long[] { (long) 3, (long) 4, (long) 5, (long) 6, (long) 7 },
				instance.testSliceLongType(new long[] { (long) 1, (long) 2,
						(long) 3, (long) 4, (long) 5, (long) 6, (long) 7 }, 2));
	}

	@Test
	public void testLongTypeSliceEndIndex() {
		assertArrayEquals(new long[] { (long) 3, (long) 4, (long) 5 },
				instance.testSliceLongType(new long[] { (long) 1, (long) 2,
						(long) 3, (long) 4, (long) 5, (long) 6, (long) 7 }, 2,
						5));
	}

	@Test
	public void testFloatTypeSlice() {
		assertArrayEquals(
				new float[] { (float) 3.3, (float) 4.4, (float) 5.5,
						(float) 6.6, (float) 7.7 },
				instance.testSliceFloatType(new float[] { (float) 1.1,
						(float) 2.2, (float) 3.3, (float) 4.4, (float) 5.5,
						(float) 6.6, (float) 7.7 }, 2), 0.0001f);
	}

	@Test
	public void testFloatTypeSliceEndIndex() {
		assertArrayEquals(
				new float[] { (float) 3.3, (float) 4.4, (float) 5.5 },
				instance.testSliceFloatType(new float[] { (float) 1.1,
						(float) 2.2, (float) 3.3, (float) 4.4, (float) 5.5,
						(float) 6.6, (float) 7.7 }, 2, 5), 0.0001f);
	}

	@Test
	public void testDoubleTypeSlice() {
		assertArrayEquals(
				new double[] { (double) 3.3, (double) 4.4, (double) 5.5,
						(double) 6.6, (double) 7.7 },
				instance.testSliceDoubleType(new double[] { (double) 1.1,
						(double) 2.2, (double) 3.3, (double) 4.4, (double) 5.5,
						(double) 6.6, (double) 7.7 }, 2), 0.0001f);
	}

	@Test
	public void testDoubleTypeSliceEndIndex() {
		assertArrayEquals(
				new double[] { (double) 3.3, (double) 4.4, (double) 5.5 },
				instance.testSliceDoubleType(new double[] { (double) 1.1,
						(double) 2.2, (double) 3.3, (double) 4.4, (double) 5.5,
						(double) 6.6, (double) 7.7 }, 2, 5), 0.0001f);
	}
    
	@Test
	public void testByteTypeSort(){
		byte[] inputArray = { 2, 1, 0 };
		byte[] nullArray = null;
		byte[] expectedArray = { 0, 1, 2 };

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expectedArray, instance.testSortByteType(inputArray));
	}
	
	@Test
	public void testShortTypeSort(){
		short[] inputArray = { 2, 1, 0 };
		short[] nullArray = null;
		short[] expectedArray = { 0, 1, 2 };

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expectedArray, instance.testSortShortType(inputArray));
	}
	
	@Test
	public void testIntegerTypeSort(){
		int[] inputArray = { 2, 1, 0 };
		int[] nullArray = null;
		int[] expectedArray = { 0, 1, 2 };

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expectedArray, instance.testSortIntegerType(inputArray));
	}
	
	@Test
	public void testLongTypeSort(){
		long[] inputArray = { 2, 1, 0 };
		long[] nullArray = null;
		long[] expectedArray = { 0, 1, 2 };

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expectedArray, instance.testSortLongType(inputArray));
	}
	
	@Test
	public void testFloatTypeSort(){
		float[] inputArray = { 2.1f, 1.1f, -0.4f };
		float[] nullArray = null;
		float[] expectedArray = { -0.4f, 1.1f, 2.1f };

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expectedArray, instance.testSortFloatType(inputArray), 0.00001f );
	}
	
	@Test
	public void testDoubleTypeSort(){
		double[] inputArray = { 2.1, 1.1, -0.4 };
		double[] nullArray = null;
		double[] expectedArray = { -0.4, 1.1, 2.1 };

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expectedArray, instance.testSortDoubleType(inputArray), 0.00001f);
	}
	
	@Test
	public void testByteSort(){
		Byte[] inputArray = { 2, 1, 0 };
		Byte[] nullArray = null;
		Byte[] expectedArray = { 0, 1, 2 };

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expectedArray, instance.testSortByte(inputArray));
	}
	
	@Test
	public void testShortSort(){
		Short[] inputArray = { 2, 1, 0 };
		Short[] nullArray = null;
		Short[] expectedArray = { 0, 1, 2 };

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expectedArray, instance.testSortShort(inputArray));
	}
	
	@Test
	public void testIntegerSort(){
		Integer[] inputArray = { 2, 1, 0 };
		Integer[] nullArray = null;
		Integer[] expectedArray = { 0, 1, 2 };

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expectedArray, instance.testSortInteger(inputArray));
	}
	
	@Test
	public void testLongSort(){
		Long[] inputArray = { 2l, 1l, 0l };
		Long[] nullArray = null;
		Long[] expectedArray = { 0l, 1l, 2l };

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expectedArray, instance.testSortLong(inputArray));
	}
	
	@Test
	public void testFloatSort(){
		Float[] inputArray = { 2.1f, 1.1f, -0.4f };
		Float[] nullArray = null;
		Float[] expectedArray = { -0.4f, 1.1f, 2.1f };

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expectedArray, instance.testSortFloat(inputArray));
	}
	
	@Test
	public void testDoubleSort(){
		Double[] inputArray = { 2.1, 1.1, -0.4 };
		Double[] nullArray = null;
		Double[] expectedArray = { -0.4, 1.1, 2.1 };

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expectedArray, instance.testSortDouble(inputArray));
	}
	
	
	@Test
	public void testByteValueSort(){
		ByteValue[] inputArray = { new ByteValue("2"), new ByteValue("1"), new ByteValue("0") };
		ByteValue[] nullArray = null;
		ByteValue[] expectedArray = { new ByteValue("0"), new ByteValue("1"), new ByteValue("2") };

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expectedArray, instance.testSortByteValue(inputArray));
	}
	
	@Test
	public void testShortValueSort(){
		ShortValue[] inputArray = { new ShortValue((short) 2), new ShortValue("1"), new ShortValue("0") };
		ShortValue[] nullArray = null;
		ShortValue[] expectedArray = { new ShortValue("0"), new ShortValue("1"), new ShortValue("2") };

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expectedArray, instance.testSortShortValue(inputArray));
	}
	
	@Test
	public void testIntegerValueSort(){
		IntValue[] inputArray = { new IntValue(2), new IntValue(1), new IntValue(0) };
		IntValue[] nullArray = null;
		IntValue[] expectedArray = { new IntValue(0), new IntValue(1), new IntValue(2) };

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expectedArray, instance.testSortIntegerValue(inputArray));
	}
	
	@Test
	public void testLongValueSort(){
		LongValue[] inputArray = { new LongValue(2l), new LongValue(1l), new LongValue(0l) };
		LongValue[] nullArray = null;
		LongValue[] expectedArray = { new LongValue(0l), new LongValue(1l), new LongValue(2l) };

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expectedArray, instance.testSortLongValue(inputArray));
	}
	
	@Test
	public void testFloatValueSort(){
		FloatValue[] inputArray = { new FloatValue(2.1f), new FloatValue(1.1f), new FloatValue(-0.4f) };
		FloatValue[] nullArray = null;
		FloatValue[] expectedArray = { new FloatValue(-0.4f), new FloatValue(1.1f), new FloatValue(2.1f) };

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expectedArray, instance.testSortFloatValue(inputArray));
	}
	
	@Test
	public void testDoubleValueSort(){
		DoubleValue[] inputArray = { new DoubleValue(2.1), new DoubleValue(1.1), new DoubleValue(-0.4) };
		DoubleValue[] nullArray = null;
		DoubleValue[] expectedArray = { new DoubleValue(-0.4), new DoubleValue(1.1), new DoubleValue(2.1) };

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expectedArray, instance.testSortDoubleValue(inputArray));
	}
	
	@Test
	public void testBigIntegerSort(){
		BigInteger[] inputArray = { BigInteger.valueOf(2), BigInteger.valueOf(1), BigInteger.valueOf(-0) };
		BigInteger[] nullArray = null;
		BigInteger[] expectedArray = { BigInteger.valueOf(-0), BigInteger.valueOf(1), BigInteger.valueOf(2) };

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expectedArray, instance.testSortBigInteger(inputArray));
	}
	
	@Test
	public void testBigDecimalSort(){
		BigDecimal[] inputArray = { BigDecimal.valueOf(2.3), BigDecimal.valueOf(1.9), BigDecimal.valueOf(-0.1) };
		BigDecimal[] nullArray = null;
		BigDecimal[] expectedArray = { BigDecimal.valueOf(-0.1), BigDecimal.valueOf(1.9), BigDecimal.valueOf(2.3) };

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expectedArray, instance.testSortBigDecimal(inputArray));
	}
	
	@Test
	public void testBigIntegerValueSort(){
		BigIntegerValue[] inputArray = { new BigIntegerValue("2"), new BigIntegerValue("1"), new BigIntegerValue("-0") };
		BigIntegerValue[] nullArray = null;
		BigIntegerValue[] expectedArray = { new BigIntegerValue("-0"), new BigIntegerValue("1"), new BigIntegerValue("2") };

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expectedArray, instance.testSortBigIntegerValue(inputArray));
	}
	
	@Test
	public void testBigDecimalValueSort(){
		BigDecimalValue[] inputArray = { new BigDecimalValue("2.3"), new BigDecimalValue("1.9"), new BigDecimalValue("-0.1") };
		BigDecimalValue[] nullArray = null;
		BigDecimalValue[] expectedArray = { new BigDecimalValue("-0.1"), new BigDecimalValue("1.9"), new BigDecimalValue("2.2") };

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expectedArray, instance.testSortBigDecimalValue(inputArray));
	}
		
	@Test
    public void testStringSort() {

        String[] nullArray = null;
        String[] strValueArray = { null, "asd", "ac", null, null };
        String[] expecteds = { "ac", "asd", null, null, null };
        String[] actuals = instance.testSortString(strValueArray);

        assertNull(RulesUtils.sort(nullArray));
        assertArrayEquals(expecteds, actuals);

    }
	
	@Test
    public void testStringValueSort() {
        StringValue[] strValueArray = { null, new StringValue("asd"), new StringValue("ac"), null, null };
        StringValue[] expecteds = { new StringValue("ac"), new StringValue("asd"), null, null, null };
        StringValue[] actuals = instance.testSortStringValue(strValueArray);

        assertArrayEquals(expecteds, actuals);

    }

    @Test
    public void testDateSort() {
        
        int year = 2013;
        int month = 1;
        int date = 25;
        int hour = 15;
        int min = 3;
        Calendar c = Calendar.getInstance();
        Locale.setDefault(Locale.ENGLISH);
               
        c.set(year, month, date, hour, min);

        Date[] nullDateArray = null;
        Date[] nullDateArrayValue = { null, c.getTime(), c.getTime() };
        Date[] actuals = instance.testSortDate(nullDateArrayValue);
        Date[] expecteds = {c.getTime(), c.getTime(), null };
        
        assertNull(RulesUtils.sort(nullDateArray));
        assertArrayEquals(expecteds, actuals);
    }
    
    @Test
    public void testObjectValueSort() {
        ObjectValue[] strValueArray = { null, new ObjectValue("asd"), new ObjectValue("ac"), null, null };
        ObjectValue[] expecteds = { new ObjectValue("ac"), new ObjectValue("asd"), null, null, null };
        ObjectValue[] actuals = instance.testSortObjectValue(strValueArray);

        assertArrayEquals(expecteds, actuals);
    }
	
    @Test
    public void testObjectInObjectArrContains(){
    	Object searchFor = new ObjectValue("5");
    	Object searchForFailed = new ObjectValue("666");
    	Object[] searchIn = {new ObjectValue("1"), new ObjectValue("4"), new ObjectValue("5"), new ObjectValue("7"), new ObjectValue("10")};
    	
    	assertFalse(instance.testContainsObjectInObjectArr(null, searchFor));
    	assertFalse(instance.testContainsObjectInObjectArr(searchIn, searchForFailed));
    	assertTrue(instance.testContainsObjectInObjectArr(searchIn, searchFor));
    }
    
    @Test
    public void testIntegerInIntegerArrContains(){
    	int searchFor = 5;
    	int searchForFailed = 666;
    	int[] searchIn = {1, 4, 5, 7, 10};
    	
    	assertFalse(instance.testContainsIntegerInIntegerArr(null, searchFor));
    	assertFalse(instance.testContainsIntegerInIntegerArr(searchIn, searchForFailed));
    	assertTrue(instance.testContainsIntegerInIntegerArr(searchIn, searchFor));
    }
    
    @Test
    public void testLongInLongArrContains(){
    	long searchFor = 5l;
    	long searchForFailed = 666l;
    	long[] searchIn = {1l, 4l, 5l, 7l, 10l};
    	
    	assertFalse(instance.testContainsLongInLongArr(null, searchFor));
    	assertFalse(instance.testContainsLongInLongArr(searchIn, searchForFailed));
    	assertTrue(instance.testContainsLongInLongArr(searchIn, searchFor));
    }
    
    @Test
    public void testByteInByteArrContains(){
    	byte searchFor = 5;
    	byte searchForFailed = 127;
    	byte[] searchIn = {1, 4, 5, -7, 10};
    	
    	assertFalse(instance.testContainsByteInByteArr(null, searchFor));
    	assertFalse(instance.testContainsByteInByteArr(searchIn, searchForFailed));
    	assertTrue(instance.testContainsByteInByteArr(searchIn, searchFor));
    }
    
    @Test
    public void testShortInShortArrContains(){
    	short searchFor = 5;
    	short searchForFailed = 32767;
    	short[] searchIn = {1, 4, 5, -7, 10};
    	
    	assertFalse(instance.testContainsShortInShortArr(null, searchFor));
    	assertFalse(instance.testContainsShortInShortArr(searchIn, searchForFailed));
    	assertTrue(instance.testContainsShortInShortArr(searchIn, searchFor));
    }
    
    @Test
    public void testCharInCharArrContains(){
    	char searchFor = 'Z';
    	char searchForFailed = 'X';
    	char[] searchIn = {'a', 'b', 'c', 'Z', 'P'};
    	
    	assertFalse(instance.testContainsCharInCharArr(null, searchFor));
    	assertFalse(instance.testContainsCharInCharArr(searchIn, searchForFailed));
    	assertTrue(instance.testContainsCharInCharArr(searchIn, searchFor));
    }
    
    @Test
    public void testFloatInFloatArrContains(){
    	float searchFor = 5.7f;
    	float searchForFailed = 32767.321f;
    	float[] searchIn = {1.01f, 4.0f, 5.7f, -7.7f, 10.3f};
    	
    	assertFalse(instance.testContainsFloatInFloatArr(null, searchFor));
    	assertFalse(instance.testContainsFloatInFloatArr(searchIn, searchForFailed));
    	assertTrue(instance.testContainsFloatInFloatArr(searchIn, searchFor));
    }
    
    @Test
    public void testDoubleInDoubleArrContains(){
    	double searchFor = 5.7;
    	double searchForFailed = 32767.321;
    	double[] searchIn = {1.01, 4.0, 5.7, -7.7, 10.3};
    	
    	assertFalse(instance.testContainsDoubleInDoubleArr(null, searchFor));
    	assertFalse(instance.testContainsDoubleInDoubleArr(searchIn, searchForFailed));
    	assertTrue(instance.testContainsDoubleInDoubleArr(searchIn, searchFor));
    }
    
    @Test
    public void testBoolInBoolArrContains(){
    	boolean searchFor = true;
    	boolean searchForFailed = false;
    	boolean[] searchIn = {true, true, true, true, true};
    	
    	assertFalse(instance.testContainsBoolInBoolArr(null, searchFor));
    	assertFalse(instance.testContainsBoolInBoolArr(searchIn, searchForFailed));
    	assertTrue(instance.testContainsBoolInBoolArr(searchIn, searchFor));
    }
    
    //@Test
    public void testObjectArrInObjectArrContains(){
    	Object[] searchFor = {new ObjectValue("5"), new ObjectValue("1")};
    	Object[] searchForFailed = {new ObjectValue("666")};
    	Object[] searchIn = {new ObjectValue("1"), new ObjectValue("4"), new ObjectValue("5"), new ObjectValue("7"), new ObjectValue("10")};
    	
    	assertFalse(instance.testContainsObjectArrInObjectArr(searchIn, null));
    	assertFalse(instance.testContainsObjectArrInObjectArr(searchIn, searchForFailed));
    	assertTrue(instance.testContainsObjectArrInObjectArr(searchIn, searchFor));
    }
    
    @Test
    public void testIntegerArrInIntegerArrContains(){
    	int[] searchFor = {5, 1};
    	int[] searchForFailed = {666};
    	int[] searchIn = {1, 4, 5, 7, 10};
    	
    	assertFalse(instance.testContainsIntegerArrInIntegerArr(searchIn, null));
    	assertFalse(instance.testContainsIntegerArrInIntegerArr(searchIn, searchForFailed));
    	assertTrue(instance.testContainsIntegerArrInIntegerArr(searchIn, searchFor));
    }
    
    @Test
    public void testLongArrInLongArrContains(){
    	long[] searchFor = {5l, 7l};
    	long[] searchForFailed = {666l};
    	long[] searchIn = {1l, 4l, 5l, 7l, 10l};
    	
    	assertFalse(instance.testContainsLongArrInLongArr(searchIn, null));
    	assertFalse(instance.testContainsLongArrInLongArr(searchIn, searchForFailed));
    	assertTrue(instance.testContainsLongArrInLongArr(searchIn, searchFor));
    }
    
    @Test
    public void testByteArrInByteArrContains(){
    	byte[] searchFor = {5, -7};
    	byte[] searchForFailed = {127};
    	byte[] searchIn = {1, 4, 5, -7, 10};
    	
    	assertFalse(instance.testContainsByteArrInByteArr(searchIn, null));
    	assertFalse(instance.testContainsByteArrInByteArr(searchIn, searchForFailed));
    	assertTrue(instance.testContainsByteArrInByteArr(searchIn, searchFor));
    }
    
    @Test
    public void testShortArrInShortArrContains(){
    	short[] searchFor = {5, -7};
    	short[] searchForFailed = {32767};
    	short[] searchIn = {1, 4, 5, -7, 10};
    	
    	assertFalse(instance.testContainsShortArrInShortArr(searchIn, null));
    	assertFalse(instance.testContainsShortArrInShortArr(searchIn, searchForFailed));
    	assertTrue(instance.testContainsShortArrInShortArr(searchIn, searchFor));
    }
    
    @Test
    public void testFloatArrInFloatArrContains(){
    	float[] searchFor = {5.7f, -7.7f};
    	float[] searchForFailed = {32767.321f};
    	float[] searchIn = {1.01f, 4.0f, 5.7f, -7.7f, 10.3f};
    	
    	assertFalse(instance.testContainsFloatArrInFloatArr(searchIn, null));
    	assertFalse(instance.testContainsFloatArrInFloatArr(searchIn, searchForFailed));
    	assertTrue(instance.testContainsFloatArrInFloatArr(searchIn, searchFor));
    }
    
    @Test
    public void testDoubleArrInDoubleArrContains(){
    	double[] searchFor = {5.7, -7.7};
    	double[] searchForFailed = {32767.321};
    	double[] searchIn = {1.01, 4.0, 5.7, -7.7, 10.3};
    	
    	assertFalse(instance.testContainsDoubleArrInDoubleArr(searchIn, null));
    	assertFalse(instance.testContainsDoubleArrInDoubleArr(searchIn, searchForFailed));
    	assertTrue(instance.testContainsDoubleArrInDoubleArr(searchIn, searchFor));
    }
    
    @Test
    public void testBoolArrInBoolArrContains(){
    	boolean[] searchFor = {true, true};
    	boolean[] searchForFailed = {false};
    	boolean[] searchIn = {true, true, true, true, true};
    	
    	assertFalse(instance.testContainsBoolArrInBoolArr(searchIn, null));
    	assertFalse(instance.testContainsBoolArrInBoolArr(searchIn, searchForFailed));
    	assertTrue(instance.testContainsBoolArrInBoolArr(searchIn, searchFor));
    }
    
    @Test
    public void testCharArrInCharArrContains(){
    	char[] searchFor = {'Z', 'P'};
    	char[] searchForFailed = {'X'};
    	char[] searchIn = {'a', 'b', 'c', 'Z', 'P'};
    	
    	assertFalse(instance.testContainsCharArrInCharArr(searchIn, null));
    	assertFalse(instance.testContainsCharArrInCharArr(searchIn, searchForFailed));
    	assertTrue(instance.testContainsCharArrInCharArr(searchIn, searchFor));
    }
    
    @Test
    public void testContainsString() {
        String searchString = "string";
        char searchChar = 's';

        assertTrue(RulesUtils.contains(str, searchString));
        assertTrue(RulesUtils.contains(str, searchChar));
        assertFalse(RulesUtils.contains(nullStr, searchChar));
        assertFalse(RulesUtils.contains("", searchChar));
    }

    @Test
    public void testContainsAny() {
        char[] searchChars = { 's', 'i', 'g' };
        String searchStr = "value";

        assertTrue(RulesUtils.containsAny(str, searchChars));
        assertTrue(RulesUtils.containsAny(str, searchStr));
        assertFalse(RulesUtils.containsAny(nullStr, searchStr));
        assertFalse(RulesUtils.containsAny("", searchStr));
    }
	
    @Test
    public void testObjectIndexOf(){
    	assertEquals(-1, instance.testIndexOfObject(null, (Object) 3));
    	assertEquals(-1, instance.testIndexOfObject(new Object[]{1, 2, 3, 4, 5}, (Object) 9));
    	assertEquals(-1, instance.testIndexOfObject(new Object[]{1, 2, 3, 4, 5}, null));
    	assertEquals(2, instance.testIndexOfObject(new Object[]{1, 2, 3, 4, 5}, (Object) 3));
    }
    
    @Test
    public void testIntegerIndexOf(){
    	assertEquals(-1, instance.testIndexOfInteger(null, 3));
    	assertEquals(-1, instance.testIndexOfInteger(new int[]{1, 2, 3, 4, 5}, 9));
    	assertEquals(2, instance.testIndexOfInteger(new int[]{1, 2, 3, 4, 5}, 3));
    }
    
    @Test
    public void testLongIndexOf(){
    	assertEquals(-1, instance.testIndexOfLong(null, 3l));
    	assertEquals(-1, instance.testIndexOfLong(new long[]{1, 2, 3, 4, 5}, 9l));
    	assertEquals(2, instance.testIndexOfLong(new long[]{1, 2, 3, 4, 5}, 3l));
    }
    
    @Test
    public void testByteIndexOf(){
    	assertEquals(-1, instance.testIndexOfByte(null, (byte) 3));
    	assertEquals(-1, instance.testIndexOfByte(new byte[]{1, 2, 3, 4, 5}, (byte) 9));
    	assertEquals(2, instance.testIndexOfByte(new byte[]{1, 2, 3, 4, 5}, (byte) 3));
    }
    
    @Test
    public void testShortIndexOf(){
    	assertEquals(-1, instance.testIndexOfShort(null, (short) 3));
    	assertEquals(-1, instance.testIndexOfShort(new short[]{1, 2, 3, 4, 5}, (short) 9));
    	assertEquals(2, instance.testIndexOfShort(new short[]{1, 2, 3, 4, 5}, (short) 3));
    }
    
    @Test
    public void testCharIndexOf(){
    	assertEquals(-1, instance.testIndexOfChar(null, '3'));
    	assertEquals(-1, instance.testIndexOfChar(new char[]{'1', '2', '3', '4', '5'}, '9'));
    	assertEquals(2, instance.testIndexOfChar(new char[]{'1', '2', '3', '4', '5'}, '3'));
    }
    
    @Test
    public void testFloatIndexOf(){
    	assertEquals(-1, instance.testIndexOfFloat(null, 3f));
    	assertEquals(-1, instance.testIndexOfFloat(new float[]{1, 2, 3, 4, 5}, 9f));
    	assertEquals(2, instance.testIndexOfFloat(new float[]{1, 2, 3, 4, 5}, 3f));
    }
    
    @Test
    public void testDoubleIndexOf(){
    	assertEquals(-1, instance.testIndexOfDouble(null, 3.3));
    	assertEquals(-1, instance.testIndexOfDouble(new double[]{1.1, 2.2, 3.3, 4.4, 5.5}, 9.9));
    	assertEquals(2, instance.testIndexOfDouble(new double[]{1.1, 2.2, 3.3, 4.4, 5.5}, 3.3));
    }
    
    @Test
    public void testBoolIndexOf(){
    	assertEquals(-1, instance.testIndexOfBool(null, false));
    	assertEquals(-1, instance.testIndexOfBool(new boolean[]{true, true, true, true, true}, false));
    	assertEquals(2, instance.testIndexOfBool(new boolean[]{true, true, false, true, true}, false));
    }
    
    @Test
    public void testObjectNoNulls(){
    	assertTrue(instance.testNoNullsObject(new Object[]{1, 2, 3, 5}));
    	assertFalse(instance.testNoNullsObject(new Object[]{1, null, 3, 5}));
    	assertFalse(instance.testNoNullsObject(new Object[]{1, 2, 3, null}));
    	assertFalse(instance.testNoNullsObject(new Object[]{null, 2, 3, 5}));
    	assertFalse(instance.testNoNullsObject(new Object[]{null}));
    }
    
    
    @Test (expected = OpenLRuntimeException.class)
    public void testError(){
    	instance.testError("Ya oshibka, trololo :)");
    }
    
    @Test (expected = OpenLRuntimeException.class)
    public void testErrorThrowable() throws Throwable{
    	instance.testErrorThrowable(new FileNotFoundException());
    }
    
    @Test
    public void testDoubleFormat(){
    	assertEquals("5.50", instance.formatDouble(5.5));
    }
    
    @Test
    public void testDoubleFormatWithFrm(){
    	assertEquals("5.5000", instance.formatDoubleWithFrm(5.5, "#,####0.0000"));
    }
    
    @Test
    public void testStringArrIntersection(){
    	String[] searchIn = new String[] {"abc", "def", "ghi", "jkl"};
    	String[] searchFor = new String[] {"def", "jkl"};
    	assertArrayEquals(searchFor, instance.testIntersectionStringArr(searchIn, searchFor));
    }
    
    @Test
    public void testMonthAbs(){
    	int year = 2013;
        int month = 1;
        int date = 25;
        int hour = 15;
        int min = 3;
        Calendar c = Calendar.getInstance();
        Locale.setDefault(Locale.ENGLISH);
               
        c.set(year, month, date, hour, min);
        
    	Date dateNow = c.getTime();
    	assertEquals(24157, instance.testAbsMonth(dateNow));
    }
    
    @Test
    public void testMonthQuarter(){
    	int year = 2013;
        int month = 1;
        int date = 25;
        int hour = 15;
        int min = 3;
        Calendar c = Calendar.getInstance();
        Locale.setDefault(Locale.ENGLISH);
               
        c.set(year, month, date, hour, min);
        
    	Date dateNow = c.getTime();
    	assertEquals(8052, instance.testAbsQuarter(dateNow));
    }
    
    @Test
    public void testDateDiff(){
    	Calendar c = Calendar.getInstance();
        Locale.setDefault(Locale.ENGLISH);
               
        c.set(2013, 1, 25, 15, 3);
        Date date1 = c.getTime();
        c.set(2010, 1, 25, 15, 3);
        Date date2 = c.getTime();
        assertEquals(1096, instance.testDiffDate(date1, date2));
        assertEquals(-1096, instance.testDiffDate(date2, date1));
    }
    
    @Test
    public void testDayOfMonth(){
    	Calendar c = Calendar.getInstance();
        Locale.setDefault(Locale.ENGLISH);
               
        c.set(2013, 1, 25, 15, 3);
        Date date1 = c.getTime();
        assertEquals(25, instance.testDayOfMonth(date1));
    }
    
    //@Test
    public void testFirstDayOfQuarter(){
    	Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 4, 1);
                
        Date date1 = c.getTime();
        
        assertEquals(date1, instance.testFirstDayOfQuarter(2));
    }
    
    //@Test
    public void testLastDayOfQuarter(){
    	Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 3, c.getActualMaximum(Calendar.DAY_OF_MONTH));
                
        Date date1 = c.getTime();
        
        assertEquals(date1, instance.testLastDayOfQuarter(0));
    }
    
    @Test
    public void testLastDayOfMonth(){
    	Calendar c = Calendar.getInstance();
                
        assertEquals(c.getActualMaximum(Calendar.DAY_OF_MONTH), instance.testLastDayOfMonth(c.getTime()));
    }
    
    @Test
    public void testGetMonth(){
    	Calendar c = Calendar.getInstance();
    	
    	assertEquals(c.get(Calendar.MONTH), instance.testGetMonth(c.getTime()));
    }
    
    @Test
    public void testMonthDiff(){
    	Calendar cal = Calendar.getInstance();
    	cal.set(cal.get(Calendar.YEAR), 3, 1);
    	Date startDate = cal.getTime();
    	cal.set(cal.get(Calendar.YEAR), 5, 1);
    	Date endDate = cal.getTime();
    	assertEquals(2, instance.testMonthDiff(endDate, startDate));
    	
    	cal.set(cal.get(Calendar.YEAR), 3, 10);
    	startDate = cal.getTime();
    	assertEquals(1, instance.testMonthDiff(endDate, startDate));
    }
    
    @Test
    public void testOrCallingFromRules() {
        assertTrue(instance.checkOr());
    }

    @Test
    public void testXOR3arguments() {
        assertFalse(callXor(false, false, false));

        assertTrue(callXor(true, false, false));

        assertTrue(callXor(false, true, false));

        assertFalse(callXor(true, true, false));

        assertTrue(callXor(false, false, true));

        assertFalse(callXor(true, false, true));

        assertFalse(callXor(false, true, true));

        assertTrue(callXor(true, true, true));
    }

    @Test
    public void testXOR2arguments() {
        assertFalse(callXor(false, false));

        assertTrue(callXor(false, true));

        assertTrue(callXor(true, false));

        assertFalse(callXor(true, true));
    }

    @Test
    public void testOR2arguments() {
        assertFalse(callOr(false, false));

        assertTrue(callOr(false, true));

        assertTrue(callOr(true, false));

        assertTrue(callOr(true, true));
    }

    @Test
    public void testOR3arguments() {
        assertFalse(callOr(false, false, false));

        assertTrue(callOr(true, false, false));

        assertTrue(callOr(false, true, false));

        assertTrue(callOr(true, true, false));

        assertTrue(callOr(false, false, true));

        assertTrue(callOr(true, false, true));

        assertTrue(callOr(false, true, true));

        assertTrue(callOr(true, true, true));
    }

    @Test
    public void testRoundToLong() {
        assertEquals(1, RulesUtils.round(1.222235345345));

        assertEquals(2, RulesUtils.round(1.500000001235345345));

        assertEquals(0, RulesUtils.round(0));
    }

    @Test
    public void testRoundWithPrecision() {
        assertEquals("1.222", String.valueOf(RulesUtils.round(1.222235345345, 3)));

        assertEquals("1.6", String.valueOf(RulesUtils.round(1.56000001235345345, 1)));

        assertEquals("0.0", String.valueOf(RulesUtils.round(0, 0)));
    }

    private boolean callXor(boolean... values) {
        return RulesUtils.xor(values);
    }

    private boolean callOr(boolean... values) {
        return RulesUtils.anyTrue(values);
    }

    /* Tests for testing isEmpty methods */
    @Test
    public void testObjectEmptyArray() {
        Object[] array = null;
        assertTrue(RulesUtils.isEmpty(array));

        array = new Object[2];
        assertFalse(RulesUtils.isEmpty(array));
    }

    @Test
    public void testByteEmptyArray() {
        byte[] array = null;
        assertTrue(RulesUtils.isEmpty(array));

        array = new byte[5];
        assertFalse(RulesUtils.isEmpty(array));
    }

    @Test
    public void testCharEmptyArray() {
        char[] array = null;
        assertTrue(RulesUtils.isEmpty(array));

        array = new char[5];
        assertFalse(RulesUtils.isEmpty(array));
    }

    @Test
    public void testShortEmptyArray() {
        short[] array = null;
        assertTrue(RulesUtils.isEmpty(array));

        array = new short[5];
        assertFalse(RulesUtils.isEmpty(array));
    }

    @Test
    public void testIntEmptyArray() {
        int[] array = null;
        assertTrue(RulesUtils.isEmpty(array));

        array = new int[5];
        assertFalse(RulesUtils.isEmpty(array));
    }

    @Test
    public void testLongEmptyArray() {
        long[] array = null;
        assertTrue(RulesUtils.isEmpty(array));

        array = new long[5];
        assertFalse(RulesUtils.isEmpty(array));
    }

    @Test
    public void testFloatEmptyArray() {
        float[] array = null;
        assertTrue(RulesUtils.isEmpty(array));

        array = new float[5];
        assertFalse(RulesUtils.isEmpty(array));
    }

    @Test
    public void testDoubleEmptyArray() {
        double[] array = null;
        assertTrue(RulesUtils.isEmpty(array));

        array = new double[5];
        assertFalse(RulesUtils.isEmpty(array));
    }

    @Test
    public void testDateEmptyArray() {
        Date[] array = null;
        assertTrue(RulesUtils.isEmpty(array));

        array = new Date[5];
        assertFalse(RulesUtils.isEmpty(array));
    }

    @Test
    public void testBigDecimalEmptyArray() {
        BigDecimal[] array = null;
        assertTrue(RulesUtils.isEmpty(array));

        array = new BigDecimal[5];
        assertFalse(RulesUtils.isEmpty(array));
    }

    @Test
    public void testBigIntegerEmptyArray() {
        BigInteger[] array = null;
        assertTrue(RulesUtils.isEmpty(array));

        array = new BigInteger[5];
        assertFalse(RulesUtils.isEmpty(array));
    }

    @Test
    public void testEmptyString() {
        String str = null;
        assertTrue(RulesUtils.isEmpty(str));

        str = "";
        assertTrue(RulesUtils.isEmpty(str));

        str = " ";
        assertTrue(RulesUtils.isEmpty(str));

        str = "  str  ";
        assertFalse(RulesUtils.isEmpty(str));

        str = "str";
        assertFalse(RulesUtils.isEmpty(str));
    }

    @Test
    public void testStartsWith() {

        String prefix = "Test";
        assertTrue(RulesUtils.startsWith(str, prefix));

        String str2 = null;
        assertFalse(RulesUtils.startsWith(str2, prefix));
    }

    @Test
    public void testSubString() {
        int beginIndex = 3;
        int endIndex = 5;

        assertEquals("ting string value", RulesUtils.substring(str, beginIndex));
        assertEquals("ti", RulesUtils.substring(str, beginIndex, endIndex));
        assertEquals("", RulesUtils.substring("", beginIndex));
        assertEquals(null, RulesUtils.substring(null, 0));

    }

    @Test
    public void testRemoveStart() {
        String remove = "Testing";

        assertEquals(" string value", RulesUtils.removeStart(str, remove));
        assertEquals(null, RulesUtils.removeStart(null, remove));
        assertEquals("", RulesUtils.removeStart("", remove));
    }

    @Test
    public void testRemoveEnd() {
        String remove = "value";

        assertEquals("Testing string ", RulesUtils.removeEnd(str, remove));
        assertEquals(null, RulesUtils.removeEnd(null, remove));
        assertEquals("", RulesUtils.removeEnd("", remove));
    }

    @Test
    public void testStringCase() {
        String str = "Testing";

        assertEquals("TESTING", RulesUtils.upperCase(str));
        assertEquals("testing", RulesUtils.lowerCase(str));
        assertEquals(null, RulesUtils.upperCase(null));
        assertEquals("", RulesUtils.upperCase(""));
    }

    @Test
    public void testReplace() {
        String text = "value Teting value string value";

        assertEquals("Testing string text", RulesUtils.replace(str, "value", "text"));
        assertEquals("text Teting text string text", RulesUtils.replace(text, "value", "text", 3));
        assertEquals(null, RulesUtils.replace(null, "value", "text"));
        assertEquals("", RulesUtils.replace("", "value", "text"));
    }

    
    
    @SuppressWarnings("deprecation")
    @Test   
    public void testDateFormat() {
        int year = 2013;
        int month = 1;
        int date = 25;
        int hour = 15;
        int min = 3;
        Calendar c = Calendar.getInstance();
        Locale.setDefault(Locale.ENGLISH);
               
        c.set(year, month, date, hour, min);

        //System.out.println("Default locale is: " + Locale.getDefault());
        //System.out.println("Locale date format: " + RulesUtils.dateToString(c.getTime()));

        assertEquals("2/25/13", RulesUtils.format(c.getTime()) );
        assertEquals("2/25/13", RulesUtils.dateToString(c.getTime()));

        assertEquals("25/13", RulesUtils.format(c.getTime(), "dd/yy"));
        assertEquals("25/13", RulesUtils.dateToString(c.getTime(), "dd/yy") );

        assertEquals("25/13 15:03", RulesUtils.format(c.getTime(), "dd/yy HH:mm"));
        assertEquals("25/13 15:03", RulesUtils.dateToString(c.getTime(), "dd/yy HH:mm"));

    }
    
    @Test
    public void quotientIntTest () {
        assertEquals(2, RulesUtils.quotient(9, 4));
    }
}
