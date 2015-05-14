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

package sk.ryznar.certificatevalidator.parser;

import java.util.HashMap;

public abstract class AbstractDNParser
{
	protected final String CIARKA_TEXT = "XXXX-XXXX_XXXX-XXXX";
	protected final String UNKNOWN = "Unknown";
	protected HashMap<String,String> values = new HashMap<>();

	protected AbstractDNParser(String s)
	{
		String secure1 = s.replaceAll("\\\\,", CIARKA_TEXT);
		String[] array = secure1.split(",");
		for (String item : array)
		{
			String[] keyValue = item.split("=");
			values.put(keyValue[0], keyValue[1].replaceAll(CIARKA_TEXT, ","));
		}
	}

	public String getOrganisation()
	{
		return values.get("O");
	}

	public String getCommonName()
	{
		return values.get("CN");
	}

	public String getUnit()
	{
		return values.get("OU");
	}
}
