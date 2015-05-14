/*
Certificate Validator is a mobile application that is used to validate
all certificates in a device against static list of trusted certificate
authorities
Copyright (C) 2015  Karol Rýznar

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package sk.ryznar.certificatevalidator.utils;

import java.math.BigInteger;

public class StringUtils
{
	public static String toFormatedString(BigInteger bigInteger)
	{
		if (null == bigInteger)
		{
			return "";
		}
		String x = bigInteger.toString(16).toUpperCase();
		if (x.length() % 2 == 1)
		{
			x = "0" + x;
		}
		String finals = "";
		for (int i = 0; i < x.length(); i = i + 2)
		{
			if ((i + 2) < x.length()) finals += x.substring(i, i + 2) + ":";
			if ((i + 2) == x.length())
			{
				finals += x.substring(i, i + 2);

			}
		}
		return finals;
	}
}
