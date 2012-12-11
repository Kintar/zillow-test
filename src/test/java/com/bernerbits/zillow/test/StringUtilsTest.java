package com.bernerbits.zillow.test;

import java.text.ParseException;

import junit.framework.Assert;

import org.junit.Test;

public class StringUtilsTest {

	/**
	 * Positive average case test.
	 * 
	 * @throws ParseException never, if the test passes.
	 */
	@Test public void positiveInteger() throws ParseException {
		Assert.assertEquals(1234, StringUtils.stringToLong("1234"));
	}
	
	/**
	 * Negative average case test.
	 * 
	 * @throws ParseException never, if the test passes.
	 */
	@Test public void negativeInteger() throws ParseException {
		Assert.assertEquals(-5678, StringUtils.stringToLong("-5678"));
	}
	
	/**
	 * Test zero.
	 * 
	 * @throws ParseException never, if the test passes.
	 */
	@Test public void zero() throws ParseException {
		Assert.assertEquals(0, StringUtils.stringToLong("0"));
	}
	

	/**
	 * Test multiple zeroes.
	 * 
	 * @throws ParseException never, if the test passes.
	 */
	@Test public void multiZeroes() throws ParseException {
		Assert.assertEquals(0, StringUtils.stringToLong("000"));
	}
	
	/**
	 * Test negative zero.
	 * 
	 * @throws ParseException never, if the test passes.
	 */
	@Test public void negativeZero() throws ParseException {
		Assert.assertEquals(0, StringUtils.stringToLong("-0"));
	}
	
	/**
	 * Test negative with multiple zeroes.
	 * 
	 * @throws ParseException never, if the test passes.
	 */
	@Test public void negativeMultiZero() throws ParseException {
		Assert.assertEquals(0, StringUtils.stringToLong("-000"));
	}

	/**
	 * Test leading zeroes.
	 * 
	 * @throws ParseException never, if the test passes.
	 */
	@Test public void leadingZeroes() throws ParseException {
		Assert.assertEquals(456, StringUtils.stringToLong("000456"));
	}
	
	/**
	 * Test negative leading zeroes.
	 * 
	 * @throws ParseException never, if the test passes.
	 */
	@Test public void negativeLeadingZeroes() throws ParseException {
		Assert.assertEquals(-4242, StringUtils.stringToLong("-0004242"));
	}
	
	
	
	/**
	 * Test empty string.
	 * 
	 * This is a somewhat exceptional case. Whether to accept this input in favor of
	 * a simpler implementation (as here), or to guard against it at the expense of more 
	 * lines of code is a decision that should be made clear in code documentation.
	 * 
	 * @throws ParseException never, if the test passes.
	 */
	@Test public void emptyString() throws ParseException {
		Assert.assertEquals(0, StringUtils.stringToLong(""));
	}

	/**
	 * Test a minus sign with no additional data. 
	 * 
	 * This is definitely an exceptional case. Whether to accept this input in favor of
	 * a simpler implementation (as here), or to guard against it at the expense of more 
	 * lines of code is a decision that should be made clear in code documentation.
	 * 
	 * @throws ParseException never, if the test passes.
	 */
	@Test public void minusSign() throws ParseException {
		Assert.assertEquals(0, StringUtils.stringToLong("-"));
	}
	
	/**
	 * Positive edge test.
	 * 
	 * This, along with the negative edge test, is to ensure that all numbers representable by the
	 * long data type can be obtained using this utility.
     *
	 * @throws ParseException never, if the test passes.
	 */
	@Test public void maxLongValue() throws ParseException {
		Assert.assertEquals(Long.MAX_VALUE, StringUtils.stringToLong("" + Long.MAX_VALUE));
	}
	
	/**
	 * Negative edge test.
	 * 
	 * This, along with the positive edge test, is to ensure that all numbers representable by the
	 * long data type can be obtained using this utility.
	 * 
	 * @throws ParseException never, if the test passes.
	 */
	@Test public void minLongValue() throws ParseException {
		Assert.assertEquals(Long.MIN_VALUE, StringUtils.stringToLong("" + Long.MIN_VALUE));
	}
	
	/**
	 * Positive overflow test.
	 * 
	 * This is an exceptional case without an intuitive mode of degradation (like the empty negative string above). 
	 * In a real-world application, the degradation mode (e.g. allow the value to overflow, as done here, or 
	 * check for overflow and throw an exception) should be explicitly chosen and made clear in code documentation.
	 *
	 * @throws ParseException never, if the test passes.
	 */
	@Test public void positiveOverflow() throws ParseException {
		long overflowValue = StringUtils.stringToLong("9223372036854775808"); // MAX_VALUE + 1
		Assert.assertEquals(Long.MIN_VALUE, overflowValue);
		Assert.assertTrue(overflowValue < 0);
	}
	
	/**
	 * Negative overflow test.
	 * 
	 * This is an exceptional case without an intuitive mode of degradation (like the empty negative string above). 
	 * In a real-world application, the degradation mode (e.g. allow the value to overflow, as done here, or 
	 * check for overflow and throw an exception) should be explicitly chosen and made clear in code documentation.
	 * 
	 * @throws ParseException never, if the test passes.
	 */
	@Test public void negativeOverflow() throws ParseException {
		long overflowValue = StringUtils.stringToLong("-9223372036854775809"); // MIN_VALUE - 1
		Assert.assertEquals(Long.MAX_VALUE, overflowValue);
		Assert.assertTrue(overflowValue > 0);
	}
	
	/**
	 * Invalid data test: Additional minus signs.
	 * 
	 * @throws ParseException always, if the test passes.
	 */
	@Test(expected=ParseException.class) public void tooManyMinusSigns() throws ParseException {
		StringUtils.stringToLong("--123");
	}
	
	/**
	 * Invalid data test: Improperly placed minus sign.
	 * 
	 * @throws ParseException always, if the test passes.
	 */
	@Test(expected=ParseException.class) public void outOfPlaceMinusSign() throws ParseException {
		StringUtils.stringToLong("12-3");
	}
	
	/**
	 * Invalid data test: Hexadecimal
	 * 
	 * @throws ParseException always, if the test passes.
	 */
	@Test(expected=ParseException.class) public void noHexDigits() throws ParseException {
		StringUtils.stringToLong("1234567890ABCDEF");
	}
	
	/**
	 * Invalid data test: Arbitrary letters
	 * 
	 * @throws ParseException always, if the test passes.
	 */
	@Test(expected=ParseException.class) public void noArbitraryLetters() throws ParseException {
		StringUtils.stringToLong("ZXBDGF");
	}
	
	/**
	 * Invalid data test: Arbitrary symbols
	 * 
	 * @throws ParseException always, if the test passes.
	 */
	@Test(expected=ParseException.class) public void noArbitrarySymbols() throws ParseException {
		StringUtils.stringToLong("01234%$#&");
	}
	
	/**
	 * Invalid data test: Spaces
	 * 
	 * @throws ParseException always, if the test passes.
	 */
	@Test(expected=ParseException.class) public void noSpaces() throws ParseException {
		StringUtils.stringToLong(" 123 ");
	}

	/**
	 * Invalid data test: Commas
	 * 
	 * @throws ParseException always, if the test passes.
	 */
	@Test(expected=ParseException.class) public void noCommas() throws ParseException {
		StringUtils.stringToLong("1,234,567");
	}

	/**
	 * Invalid data test: Currency symbols
	 * 
	 * @throws ParseException always, if the test passes.
	 */
	@Test(expected=ParseException.class) public void noCurrency() throws ParseException {
		StringUtils.stringToLong("$1000");
	}
	
	/**
	 * Invalid data test: Decimals
	 * 
	 * @throws ParseException always, if the test passes.
	 */
	@Test(expected=ParseException.class) public void testNoDecimals() throws ParseException {
		StringUtils.stringToLong("1000.00");
	}
}
