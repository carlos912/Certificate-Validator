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

import android.util.Log;
import sk.ryznar.certificatevalidator.utils.DateUtils;

import java.util.Date;

public class CertItem
{
	private boolean trusted = false;
	private int internalId;

	private String alias;

	// subject DN
	private String subjectOrganization;
	private String subjectCommonName;
	private String subjectUnit;

	private String subjectSerialNumber;
	// issuer DN
	private String issuerOrganization;
	private String issuerCommonName;

	private String issuerUnit;
	// validity
	private Date validFrom;
	private Date validTo;

	private String sigAlgName;
	private String hashFormated;
	private String sigAlgNameFormated;

	public boolean isTrusted()
	{
		return trusted;
	}

	public void setTrusted(boolean trusted)
	{
		this.trusted = trusted;
	}

	public int getInternalId()
	{
		return internalId;
	}

	public void setInternalId(int internalId)
	{
		this.internalId = internalId;
	}

	public String getIssuerOrganization()
	{
		return issuerOrganization;
	}

	public void setIssuerOrganization(String issuerOrganization)
	{
		this.issuerOrganization = issuerOrganization;
	}

	public String getIssuerCommonName()
	{
		return issuerCommonName;
	}

	public void setIssuerCommonName(String issuerCommonName)
	{
		this.issuerCommonName = issuerCommonName;
	}

	public String getSubjectOrganization()
	{
		return subjectOrganization;
	}

	public void setSubjectOrganization(String subjectOrganization)
	{
		this.subjectOrganization = subjectOrganization;
	}

	public String getSubjectCommonName()
	{
		return subjectCommonName;
	}

	public void setSubjectCommonName(String subjectCommonName)
	{
		this.subjectCommonName = subjectCommonName;
	}

	public Date getValidFrom()
	{
		return validFrom;
	}

	public void setValidFrom(Date validFrom)
	{
		this.validFrom = validFrom;
	}

	public Date getValidTo()
	{
		return validTo;
	}

	public void setValidTo(Date validTo)
	{
		this.validTo = validTo;
	}

	public String getSigAlgName()
	{
		return sigAlgName;
	}

	public String getSigAlgNameFormated()
	{
		return sigAlgNameFormated;
	}

	public void setSigAlgName(String sigAlgName)
	{
		this.sigAlgName = sigAlgName;
		this.sigAlgNameFormated = sigAlgName;
		this.hashFormated = sigAlgName;

		int i = sigAlgNameFormated.toUpperCase().lastIndexOf("WITH");
		sigAlgNameFormated = sigAlgNameFormated.substring(i + 4);
		sigAlgNameFormated = sigAlgNameFormated.replaceAll("encryption", "");
		sigAlgNameFormated = sigAlgNameFormated.replaceAll("Encryption", "");

		hashFormated = hashFormated.substring(0, i);
		hashFormated = hashFormated.replaceAll("SHA", "SHA-");
	}

	public String toHtmlString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<br />");
		sb.append(issuerOrganization).append("<br />");
		if (trusted)
		{
			sb.append("<strong>Trusted</strong>");
		}
		else
		{
			sb.append("<strong>NOT Trusted</strong>");
		}

        sb.append("<br />").append("<br />");
        sb.append("<strong>Issued by:</strong>").append("<br />").append("<br />");
        sb.append("Common name:").append("<br />");
        sb.append(issuerCommonName).append("<br />").append("<br />");
        sb.append("Organization:").append("<br />");
        sb.append(issuerOrganization).append("<br />").append("<br />");
        sb.append("Organizational unit:").append("<br />");
        sb.append(issuerUnit==null?"":issuerUnit).append("<br />").append("<br />");

		sb.append("<strong>Issued to:</strong>").append("<br />").append("<br />");
		sb.append("Common name:").append("<br />");
		sb.append(subjectCommonName).append("<br />").append("<br />");
		sb.append("Organization:").append("<br />");
		sb.append(subjectOrganization).append("<br />").append("<br />");
		sb.append("Organizational unit:").append("<br />");
		sb.append(subjectUnit==null?"":subjectUnit).append("<br />").append("<br />");

        sb.append("<strong>Serial Number:</strong>").append("<br />").append("<br />");
        sb.append(subjectSerialNumber).append("<br />").append("<br />");
		sb.append("<strong>Validity:</strong>").append("<br />").append("<br />");
		sb.append("Valid from:").append("<br />");
		sb.append(DateUtils.getDateAsString(validFrom)).append("<br />").append("<br />");
		sb.append("Valid until:").append("<br />");
		sb.append(DateUtils.getDateAsString(validTo)).append("<br />").append("<br />");

		sb.append("<strong>Hash function:</strong>").append("<br />").append("<br />");
		sb.append(hashFormated).append("<br />").append("<br />");

		sb.append("<strong>Signature algorithm:</strong>").append("<br />").append("<br />");
		sb.append(sigAlgNameFormated);

		return sb.toString();
	}

	public String getSubjectUnit()
	{
		return subjectUnit;
	}

	public void setSubjectUnit(String subjectUnit)
	{
		this.subjectUnit = subjectUnit;
	}

	public String getSubjectSerialNumber()
	{
		return subjectSerialNumber;
	}

	public void setSubjectSerialNumber(String subjectSerialNumber)
	{
		this.subjectSerialNumber = subjectSerialNumber;
	}

	public String getIssuerUnit()
	{
		return issuerUnit;
	}

	public void setIssuerUnit(String issuerUnit)
	{
		this.issuerUnit = issuerUnit;
	}

	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	public String getAlias()
	{
		return alias;
	}
}
