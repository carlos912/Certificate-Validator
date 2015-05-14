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

package sk.ryznar.certificatevalidator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import sk.ryznar.certificatevalidator.R;
import sk.ryznar.certificatevalidator.model.CertItem;
import sk.ryznar.certificatevalidator.utils.DateUtils;

import java.util.Comparator;

public class CertArrayAdapter extends ArrayAdapter<CertItem>
{
	private final Context context;

	public CertArrayAdapter(Context context, CertItem[] objects)
	{
		super(context, R.layout.item_layout, objects);
		this.context = context;
	}

	private static CertItem[] getStringArray(CertItem[] objects)
	{
		return new CertItem[objects.length];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		CertItem item = getItem(position);

		// title
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.item_layout, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.firstLine);
		textView.setText(item.getIssuerOrganization());

		//
		TextView textCN = (TextView) rowView.findViewById(R.id.textCN);
		textCN.setText("CN: " + item.getIssuerCommonName());

		// valid from: to:
		StringBuilder sb = new StringBuilder();
		sb.append("Valid from: ");
		sb.append(DateUtils.getDateAsString(item.getValidFrom()));
		sb.append(" to: ");
		sb.append(DateUtils.getDateAsString(item.getValidTo()));
		TextView secondLine = (TextView) rowView.findViewById(R.id.validDates);
		secondLine.setText(sb.toString());

		// change the icon for valid / invalid
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		if (item.isTrusted())
		{
			imageView.setImageResource(R.drawable.valid);
		}
		else
		{
			imageView.setImageResource(R.drawable.invalid);
		}

		return rowView;
	}

	@Override
	public void sort(Comparator<? super CertItem> comparator)
	{
		super.sort(comparator);
	}

	@Override
	public long getItemId(int position)
	{
		return getItem(position).getInternalId();
	}
}
