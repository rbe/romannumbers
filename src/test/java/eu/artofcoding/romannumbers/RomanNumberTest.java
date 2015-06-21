package eu.artofcoding.romannumbers;

import org.junit.Assert;
import org.junit.Test;

public class RomanNumberTest {

    @Test
    public void testRomanToArabian() throws Exception {
        Assert.assertEquals(1903, RomanNumber.romanToArabic("MCMIII"));
        Assert.assertEquals(1944, RomanNumber.romanToArabic("MCMXLIV"));
        Assert.assertEquals(1978, RomanNumber.romanToArabic("MCMLXXVIII"));
        Assert.assertEquals(2014, RomanNumber.romanToArabic("MMXIV"));
        Assert.assertEquals(2058, RomanNumber.romanToArabic("MCMLCVIII"));
    }

}
