/*
 * #%L
 * de-metas-edi-esb-camel
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.edi.esb.ordersimport.compudata;

import java.io.Serializable;

public class H000 implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8444057375724215949L;

	private String record;
	private String sender;
	private String senderQual;
	private String receiver;
	private String receiverQual;
	private String reference;
	private String msgFormat;
	private String msgType;
	private String msgSubType;
	private String msgCount;
	private String syntaxID;
	private String syntaxVersion;
	private String messageDate;
	private String messageTime;
	private String applicationRef;
	private String commsAgreement;
	private String testFlag;

	public final String getRecord()
	{
		return record;
	}

	public final void setRecord(final String record)
	{
		this.record = record;
	}

	public final String getSender()
	{
		return sender;
	}

	public final void setSender(final String sender)
	{
		this.sender = sender;
	}

	public final String getSenderQual()
	{
		return senderQual;
	}

	public final void setSenderQual(final String senderQual)
	{
		this.senderQual = senderQual;
	}

	public final String getReceiver()
	{
		return receiver;
	}

	public final void setReceiver(final String receiver)
	{
		this.receiver = receiver;
	}

	public final String getReceiverQual()
	{
		return receiverQual;
	}

	public final void setReceiverQual(final String receiverQual)
	{
		this.receiverQual = receiverQual;
	}

	public final String getReference()
	{
		return reference;
	}

	public final void setReference(final String reference)
	{
		this.reference = reference;
	}

	public final String getMsgFormat()
	{
		return msgFormat;
	}

	public final void setMsgFormat(final String msgFormat)
	{
		this.msgFormat = msgFormat;
	}

	public final String getMsgType()
	{
		return msgType;
	}

	public final void setMsgType(final String msgType)
	{
		this.msgType = msgType;
	}

	public final String getMsgSubType()
	{
		return msgSubType;
	}

	public final void setMsgSubType(final String msgSubType)
	{
		this.msgSubType = msgSubType;
	}

	public final String getMsgCount()
	{
		return msgCount;
	}

	public final void setMsgCount(final String msgCount)
	{
		this.msgCount = msgCount;
	}

	public final String getSyntaxID()
	{
		return syntaxID;
	}

	public final void setSyntaxID(final String syntaxID)
	{
		this.syntaxID = syntaxID;
	}

	public final String getSyntaxVersion()
	{
		return syntaxVersion;
	}

	public final void setSyntaxVersion(final String syntaxVersion)
	{
		this.syntaxVersion = syntaxVersion;
	}

	public final String getMessageDate()
	{
		return messageDate;
	}

	public final void setMessageDate(final String messageDate)
	{
		this.messageDate = messageDate;
	}

	public final String getMessageTime()
	{
		return messageTime;
	}

	public final void setMessageTime(final String messageTime)
	{
		this.messageTime = messageTime;
	}

	public final String getApplicationRef()
	{
		return applicationRef;
	}

	public final void setApplicationRef(final String applicationRef)
	{
		this.applicationRef = applicationRef;
	}

	public final String getCommsAgreement()
	{
		return commsAgreement;
	}

	public final void setCommsAgreement(final String commsAgreement)
	{
		this.commsAgreement = commsAgreement;
	}

	public final String getTestFlag()
	{
		return testFlag;
	}

	public final void setTestFlag(final String testFlag)
	{
		this.testFlag = testFlag;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (applicationRef == null ? 0 : applicationRef.hashCode());
		result = prime * result + (commsAgreement == null ? 0 : commsAgreement.hashCode());
		result = prime * result + (messageDate == null ? 0 : messageDate.hashCode());
		result = prime * result + (messageTime == null ? 0 : messageTime.hashCode());
		result = prime * result + (msgCount == null ? 0 : msgCount.hashCode());
		result = prime * result + (msgFormat == null ? 0 : msgFormat.hashCode());
		result = prime * result + (msgSubType == null ? 0 : msgSubType.hashCode());
		result = prime * result + (msgType == null ? 0 : msgType.hashCode());
		result = prime * result + (receiver == null ? 0 : receiver.hashCode());
		result = prime * result + (receiverQual == null ? 0 : receiverQual.hashCode());
		result = prime * result + (record == null ? 0 : record.hashCode());
		result = prime * result + (reference == null ? 0 : reference.hashCode());
		result = prime * result + (sender == null ? 0 : sender.hashCode());
		result = prime * result + (senderQual == null ? 0 : senderQual.hashCode());
		result = prime * result + (syntaxID == null ? 0 : syntaxID.hashCode());
		result = prime * result + (syntaxVersion == null ? 0 : syntaxVersion.hashCode());
		result = prime * result + (testFlag == null ? 0 : testFlag.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) // NOPMD by al
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final H000 other = (H000)obj;
		if (applicationRef == null)
		{
			if (other.applicationRef != null)
			{
				return false;
			}
		}
		else if (!applicationRef.equals(other.applicationRef))
		{
			return false;
		}
		if (commsAgreement == null)
		{
			if (other.commsAgreement != null)
			{
				return false;
			}
		}
		else if (!commsAgreement.equals(other.commsAgreement))
		{
			return false;
		}
		if (messageDate == null)
		{
			if (other.messageDate != null)
			{
				return false;
			}
		}
		else if (!messageDate.equals(other.messageDate))
		{
			return false;
		}
		if (messageTime == null)
		{
			if (other.messageTime != null)
			{
				return false;
			}
		}
		else if (!messageTime.equals(other.messageTime))
		{
			return false;
		}
		if (msgCount == null)
		{
			if (other.msgCount != null)
			{
				return false;
			}
		}
		else if (!msgCount.equals(other.msgCount))
		{
			return false;
		}
		if (msgFormat == null)
		{
			if (other.msgFormat != null)
			{
				return false;
			}
		}
		else if (!msgFormat.equals(other.msgFormat))
		{
			return false;
		}
		if (msgSubType == null)
		{
			if (other.msgSubType != null)
			{
				return false;
			}
		}
		else if (!msgSubType.equals(other.msgSubType))
		{
			return false;
		}
		if (msgType == null)
		{
			if (other.msgType != null)
			{
				return false;
			}
		}
		else if (!msgType.equals(other.msgType))
		{
			return false;
		}
		if (receiver == null)
		{
			if (other.receiver != null)
			{
				return false;
			}
		}
		else if (!receiver.equals(other.receiver))
		{
			return false;
		}
		if (receiverQual == null)
		{
			if (other.receiverQual != null)
			{
				return false;
			}
		}
		else if (!receiverQual.equals(other.receiverQual))
		{
			return false;
		}
		if (record == null)
		{
			if (other.record != null)
			{
				return false;
			}
		}
		else if (!record.equals(other.record))
		{
			return false;
		}
		if (reference == null)
		{
			if (other.reference != null)
			{
				return false;
			}
		}
		else if (!reference.equals(other.reference))
		{
			return false;
		}
		if (sender == null)
		{
			if (other.sender != null)
			{
				return false;
			}
		}
		else if (!sender.equals(other.sender))
		{
			return false;
		}
		if (senderQual == null)
		{
			if (other.senderQual != null)
			{
				return false;
			}
		}
		else if (!senderQual.equals(other.senderQual))
		{
			return false;
		}
		if (syntaxID == null)
		{
			if (other.syntaxID != null)
			{
				return false;
			}
		}
		else if (!syntaxID.equals(other.syntaxID))
		{
			return false;
		}
		if (syntaxVersion == null)
		{
			if (other.syntaxVersion != null)
			{
				return false;
			}
		}
		else if (!syntaxVersion.equals(other.syntaxVersion))
		{
			return false;
		}
		if (testFlag == null)
		{
			if (other.testFlag != null)
			{
				return false;
			}
		}
		else if (!testFlag.equals(other.testFlag))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "H000 [record=" + record + ", sender=" + sender + ", senderQual=" + senderQual + ", receiver=" + receiver + ", receiverQual=" + receiverQual + ", reference="
				+ reference
				+ ", msgFormat=" + msgFormat + ", msgType=" + msgType + ", msgSubType=" + msgSubType + ", msgCount=" + msgCount + ", syntaxID=" + syntaxID
				+ ", syntaxVersion=" + syntaxVersion
				+ ", messageDate=" + messageDate + ", messageTime=" + messageTime + ", applicationRef=" + applicationRef + ", commsAgreement=" + commsAgreement + ", testFlag="
				+ testFlag + "]";
	}
}
