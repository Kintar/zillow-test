package com.bernerbits.zillow.test;

import java.text.ParseException;

/**
 * StringUtils, for Question 1.
 * 
 * Strategy: 
 * 
 * Check first character for minus sign. 
 * Then, initialize the result to 0.  
 * For each character "c" in the string:
 * 
 *   Multiply the result by 10 (Do this first so the last iteration is not multiplied by 10; 
 *   	for the first iteration, this does nothing.)
 *   Convert "c" to a digit.
 *   If positive, add the digit to the result. Otherwise, subtract the digit from the result.
 * 
 * Finally, return the result.
 *   
 * Known limitations:
 * 
 * 1. This strategy does not explicitly handle overflow. If the string value given exceeds Long.MAX_VALUE 
 *   or is less than Long.MIN_VALUE, the most significant bits will be chopped off. This can be acceptable,
 *   but it should be made clear.
 *   
 * 2. Niceties like spaces, comma-separated thousands, decimal points, and currency signs are not accepted.
 *   For this implementation, it is assumed that any input has been scrubbed before being handed to the
 *   parser.
 * 
 * 3. Strange inputs like long strings of zeroes, the empty string, and a single minus sign are all accepted 
 *   and return 0. Again, this can be acceptable if it is well-documented, since it tightens up the library
 *   code instead of littering it with validation checks.
 *   
 * 4. Only decimal inputs are accepted. A more robust implementation might pass 10 as a default base into a
 *   second conversion method that uses a "base" parameter instead of a hardcoded 10. The private 
 *   character-to-digit conversion function would become more complicated, as 'A' to 'Z' would now represent 
 *   possible digit values, and the digit's value would have to be checked to ensure that it is legal within
 *   the requested base.  
 *   
 * 5. Invalid data is never dealt with gracefully, opting instead to throw a ParseException when a non-numeric
 *   character is encountered. As an alternative, an input string of "123ZZZ" might still return 123 as its 
 *   result; in this situation, the first invalid character is regarded as the end of the string for purposes 
 *   of numeric conversion.
 * 
 * @author derekberner
 *
 */
public class StringUtils {

	/**
	 * Converts a string in base 10 to a long value. 
	 * 
	 * @param s A numeric string to convert.
	 * @return The numeric value of the string.
	 * @throws ParseException If a non-numeric character is encountered.
	 */
	public static long stringToLong(String s) throws ParseException {
		// Check first character for - sign; if so, treat it as negative when computing
		boolean negative = false;
		if(s.length() > 0 && s.charAt(0) == '-') {
			negative = true;
			s = s.substring(1);
		}

		// Build up result one character at a time. Index is tracked for error reporting.
		long result = 0;
		int index = 0;
		for(char c : s.toCharArray()) {
			result *= 10;
			int digit = charToDigit(c, index++);
			if(negative) {
				// A negative number can be thought of a base-10 number with a "negative digit" 
				// in each position.
				result -= digit;
			} else {
				result += digit;
			}
		}
		return result;
	}

	/**
	 * @param c The character to convert
	 * @param index The position in the string where the character is found. Used for error reporting
	 * @return The numeric value of the digit represented by c
	 * @throws ParseException If c is not a digit between 0 and 9
	 */
	private static int charToDigit(char c, int index) throws ParseException {
		// Java uses UTF-16 internally to represent characters. This means that '0' through '9' are 
		// sequentially ordered, allowing us to take this nice, readable, quick-executing shortcut.
		// Seldom are the stars so beautifully and conveniently aligned. 
		// 
		// Note that if for some reason Java (or perhaps a non-standard VM or compiler) used EBCDIC or 
		// some other such archaic standard, this would not be possible and a more explicit mapping 
		// would be required.
		if(c >= '0' && c <= '9') {
			return (c - '0');
		}
		throw new ParseException("Illegal character '" + c + "'", index);
	}

}
