package de.metas.payment.esr.dataimporter.impl.camt54;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

import com.google.common.base.Objects;

import lombok.NonNull;

/**
 * Thanks to <a href="https://stackoverflow.com/a/6747695/1012103">https://stackoverflow.com/a/6747695/1012103</a>
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/1903
 *
 */
public class MultiVersionStreamReaderDelegate extends StreamReaderDelegate
{
	public MultiVersionStreamReaderDelegate(@NonNull final XMLStreamReader xsr)
	{
		super(xsr);
	}

	/**
	 * If the actual namespace is <code>urn:iso:std:iso:20022:tech:xsd:camt.054.001.02</code> or <code>...001.04</code> or <code>...001.05</code>, then this method returns <code>...001.06</code> instead,
	 * because it works for the XML we process here, and it allows us to also process older XML files.
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/1903
	 */
	@Override
	public String getNamespaceURI()
	{
		final String namespaceURI = super.getNamespaceURI();
		if ( Objects.equal("urn:iso:std:iso:20022:tech:xsd:camt.054.001.04", namespaceURI)
				|| Objects.equal("urn:iso:std:iso:20022:tech:xsd:camt.054.001.05", namespaceURI))
		{
			// listing those two URNs that we replace is not elegant, but simple & easy. that's why I do it.
			return "urn:iso:std:iso:20022:tech:xsd:camt.054.001.06";
		}

		return namespaceURI;
	}
	
}

