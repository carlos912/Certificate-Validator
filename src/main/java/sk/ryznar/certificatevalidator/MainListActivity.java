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
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import sk.ryznar.certificatevalidator.adapter.CertArrayAdapter;
import sk.ryznar.certificatevalidator.model.CertItem;
import sk.ryznar.certificatevalidator.model.CertList;
import sk.ryznar.certificatevalidator.services.CertificateService;

import java.util.Comparator;


public class MainListActivity extends ActionBarActivity
{
	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_list);
		Resources.setContext(this);
		initList();

		/*String pwd = System.getenv("KSTOREPWD");
		Log.i("PWD", pwd);*/
	}

	private void initList()
	{
		list = (ListView) findViewById(R.id.listView);
		list.setAdapter(createArrayAdapter());
		((CertArrayAdapter) list.getAdapter()).sort(new ComparatorByOrg());

		list.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				makePopUp((int) id);
			}
		});
	}

	private PopupWindow popupWindow;

	private void makePopUp(final int itemId)
	{

		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.popup_window, null);
		popupWindow = new PopupWindow(layout, 900, 1300, true);
		popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

		TextView detail = (TextView) layout.findViewById(R.id.textDetails);
		detail.setMovementMethod(new ScrollingMovementMethod());
		detail.setText(Html.fromHtml(CertList.ITEMS_MAP.get(itemId).toHtmlString()));

		Button btnClosePopup = (Button) layout.findViewById(R.id.closeButton);
		btnClosePopup.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				popupWindow.dismiss();
			}
		});

		Button btnRemove = (Button) layout.findViewById(R.id.buttonRemove);
		btnRemove.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				CertItem item = CertList.ITEMS_MAP.get(itemId);

				String result = CertificateService.removeAsRoot(item.getAlias());
				initList();

				popupWindow.dismiss();
				Toast.makeText(Resources.getContext(), result, Toast.LENGTH_LONG).show();
			}
		});
	}

	private CertArrayAdapter createArrayAdapter()
	{
		CertList.clear();
		CertList.addAll(CertificateService.getCertificates());
		CertArrayAdapter adapter = new CertArrayAdapter(this, CertList.ITEMS.toArray(new CertItem[1]));
		return adapter;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();

		if (id == R.id.action_sort_by_name)
		{
			CertArrayAdapter adapter = (CertArrayAdapter) list.getAdapter();
			adapter.sort(new ComparatorByOrg());
			Toast.makeText(this, "Sorted by name of Organization", Toast.LENGTH_LONG).show();
			return true;
		}

		if (id == R.id.action_sort_by_date)
		{
			CertArrayAdapter adapter = (CertArrayAdapter) list.getAdapter();
			adapter.sort(new CompatatorByDate());
			Toast.makeText(this, "Sorted by date: Valid from", Toast.LENGTH_LONG).show();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private class ComparatorByOrg implements Comparator<CertItem>
	{
		@Override
		public int compare(CertItem lhs, CertItem rhs)
		{
			if (lhs.isTrusted() && !rhs.isTrusted())
			{
				return 1;
			}
			else if (!lhs.isTrusted() && rhs.isTrusted())
			{
				return -1;
			}
			else
			{
				return lhs.getIssuerOrganization().compareToIgnoreCase(rhs.getIssuerOrganization());
			}
		}
	}

	private class CompatatorByDate implements Comparator<CertItem>
	{
		@Override
		public int compare(CertItem lhs, CertItem rhs)
		{
			if (lhs.isTrusted() && !rhs.isTrusted())
			{
				return 1;
			}
			else if (!lhs.isTrusted() && rhs.isTrusted())
			{
				return -1;
			}
			else
			{
				return lhs.getValidFrom().compareTo(rhs.getValidFrom());
			}
		}
	}

	public void onItemClick(View view)
	{
		//Toast.makeText(this, view.toString() + " selected", Toast.LENGTH_LONG).show();
	}
}
