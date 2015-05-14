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

package sk.ryznar.certificatevalidator.services;

import android.util.Log;
import sk.ryznar.certificatevalidator.model.CertItem;
import sk.ryznar.certificatevalidator.parser.IssuerDNParser;
import sk.ryznar.certificatevalidator.parser.SubjectDNParser;
import sk.ryznar.certificatevalidator.utils.StringUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class CertificateService
{
	private static final String KEYSTORE_NAME = "AndroidCAStore";
	private static int ID = 0;

	public static String removeAsRoot(String alias)
	{
		try
		{
			Process su = Runtime.getRuntime().exec("su");
			DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
			StringBuilder sb = new StringBuilder();
			// keytool -delete -alias mydomain -alias ourdomain -keystore pc.keystore
			sb.append("keytool -delete -alias \"")
					.append(alias)
					.append("\" -keystore \"")
					.append(KEYSTORE_NAME)
					.append("\"\n");
			outputStream.writeBytes(sb.toString());
			outputStream.flush();
			outputStream.writeBytes("exit\n");
			outputStream.flush();
			su.waitFor();
			return "Certificate removed";
		}
		catch (IOException | InterruptedException e)
		{
			Log.e("CertificateService", e.getMessage(), e);
			return e.getClass().getSimpleName() + "\n" + "Remove works on rooted system only!";
		}
	}

	public static String remove(String alias)
	{
		try
		{
			KeyStore ks = KeyStore.getInstance(KEYSTORE_NAME);
			ks.load(null, null);

			if (ks.containsAlias(alias))
			{
				ks.deleteEntry(alias);
				ks.store(null, null);
				return "Certificate removed";
			}
			else
			{
				return "Certificate does not exist";
			}
		}
		catch (UnsupportedOperationException | KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e)
		{
			e.printStackTrace();
			return e.getClass().getSimpleName();
		}
	}

	public static List<CertItem> getCertificates()
	{
		List<CertItem> res = new ArrayList<>();
		try
		{
			KeyStore ks = KeyStore.getInstance(KEYSTORE_NAME);
			ks.load(null, null);
			Enumeration aliases = ks.aliases();
			while (aliases.hasMoreElements())
			{
				String alias = (String) aliases.nextElement();
				X509Certificate cert = (X509Certificate) ks.getCertificate(alias);

				CertItem item = new CertItem();
				item.setAlias(alias);
				item.setTrusted(false);
				item.setInternalId(ID++);

				SubjectDNParser subjectDNParser = new SubjectDNParser(cert.getSubjectDN().getName());
				item.setSubjectOrganization(subjectDNParser.getOrganisation());
				item.setSubjectCommonName(subjectDNParser.getCommonName());
				item.setSubjectUnit(subjectDNParser.getUnit());
				item.setSubjectSerialNumber(StringUtils.toFormatedString(cert.getSerialNumber()));

				IssuerDNParser issuerDNParser = new IssuerDNParser(cert.getIssuerDN().getName());
				item.setIssuerOrganization(issuerDNParser.getOrganisation());
				item.setIssuerCommonName(issuerDNParser.getCommonName());

				item.setValidFrom(cert.getNotBefore());
				item.setValidTo(cert.getNotAfter());

				item.setSigAlgName(cert.getSigAlgName());
				//Log.i("SIG", item.getSigAlgName() + " -> " + item.getSigAlgNameFormated());

				res.add(item);
			}
		}
		catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e)
		{
			e.printStackTrace();
		}
		return res;
	}
}
