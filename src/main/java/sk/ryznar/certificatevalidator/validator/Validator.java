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

package sk.ryznar.certificatevalidator.validator;

import android.util.Log;
import sk.ryznar.certificatevalidator.Resources;
import sk.ryznar.certificatevalidator.model.CertItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Validator
{
	private static List<String> trusted = null;

	public static void validate(List<CertItem> items)
	{
		if (null == trusted)
		{
			try
			{
				InputStream is = Resources.getContext().getAssets().open("TrustedCA.txt");
				setTrusted(is);
			}
			catch (IOException e)
			{
				Log.e("", e.getMessage(), e);
			}
		}
		for (CertItem item : items)
		{
			validate(item);
		}
	}

	private static void validate(CertItem item)
	{
		for (String s : trusted)
		{
			if (s.equals(item.getIssuerOrganization()))
			{
				item.setTrusted(true);
			}
		}
	}

	public static void setTrusted(InputStream inputStream)
	{
		trusted = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		try
		{
			while ((line = reader.readLine()) != null)
			{
				trusted.add(line);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
