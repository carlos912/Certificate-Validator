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

package sk.ryznar.certificatevalidator.model;

import sk.ryznar.certificatevalidator.validator.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CertList
{
	public static List<CertItem> ITEMS = new ArrayList<>();
	public static Map<Integer, CertItem> ITEMS_MAP = new HashMap<>();

	public static void addItem(CertItem item)
	{
		ITEMS.add(item);
		ITEMS_MAP.put(item.getInternalId(), item);
	}

	public static void addAll(List<CertItem> list)
	{
		for (CertItem certItem : list)
		{
			addItem(certItem);
		}
		Validator.validate(ITEMS);
	}

	public static void clear()
	{
		ITEMS = new ArrayList<>();
		ITEMS_MAP = new HashMap<>();
	}
}
