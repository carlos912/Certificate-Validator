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

package sk.ryznar.certificatevalidator;

import android.content.Context;

public class Resources
{
	private static Context context;

	public static Context getContext()
	{
		return context;
	}

	public static void setContext(Context context)
	{
		Resources.context = context;
	}
}
