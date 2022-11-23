package de.metas.externalsystem.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Interface for ExternalSystem_Config_RabbitMQ_HTTP
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Config_RabbitMQ_HTTP 
{

	String Table_Name = "ExternalSystem_Config_RabbitMQ_HTTP";

//	/** AD_Table_ID=541803 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";


	/**
	 * Set Users Group.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSubjectCreatedByUserGroup_ID (int SubjectCreatedByUserGroup_ID);

	/**
	 * Get Users Group.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSubjectCreatedByUserGroup_ID();

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, org.compiere.model.I_AD_UserGroup> COLUMN_SubjectCreatedByUserGroup_ID = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "SubjectCreatedByUserGroup_ID", org.compiere.model.I_AD_UserGroup.class);
	String COLUMNNAME_SubjectCreatedByUserGroup_ID = "SubjectCreatedByUserGroup_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set External System Config.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_ID (int ExternalSystem_Config_ID);

	/**
	 * Get External System Config.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_ID();

	I_ExternalSystem_Config getExternalSystem_Config();

	void setExternalSystem_Config(I_ExternalSystem_Config ExternalSystem_Config);

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, I_ExternalSystem_Config> COLUMN_ExternalSystem_Config_ID = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "ExternalSystem_Config_ID", I_ExternalSystem_Config.class);
	String COLUMNNAME_ExternalSystem_Config_ID = "ExternalSystem_Config_ID";

	/**
	 * Set RabbitMQ REST API Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_RabbitMQ_HTTP_ID (int ExternalSystem_Config_RabbitMQ_HTTP_ID);

	/**
	 * Get RabbitMQ REST API Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_RabbitMQ_HTTP_ID();

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, Object> COLUMN_ExternalSystem_Config_RabbitMQ_HTTP_ID = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "ExternalSystem_Config_RabbitMQ_HTTP_ID", null);
	String COLUMNNAME_ExternalSystem_Config_RabbitMQ_HTTP_ID = "ExternalSystem_Config_RabbitMQ_HTTP_ID";

	/**
	 * Set Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystemValue (String ExternalSystemValue);

	/**
	 * Get Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getExternalSystemValue();

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, Object> COLUMN_ExternalSystemValue = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "ExternalSystemValue", null);
	String COLUMNNAME_ExternalSystemValue = "ExternalSystemValue";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Auto-send if created by users group.
	 * If a business bartner was created by a user from the given group, then it is automatically send.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAutoSendWhenCreatedByUserGroup (boolean IsAutoSendWhenCreatedByUserGroup);

	/**
	 * Get Auto-send if created by users group.
	 * If a business bartner was created by a user from the given group, then it is automatically send.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAutoSendWhenCreatedByUserGroup();

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, Object> COLUMN_IsAutoSendWhenCreatedByUserGroup = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "IsAutoSendWhenCreatedByUserGroup", null);
	String COLUMNNAME_IsAutoSendWhenCreatedByUserGroup = "IsAutoSendWhenCreatedByUserGroup";

	/**
	 * Set IsSyncBPartnersToRabbitMQ.
	 * If checked, then business selected partners can be initially send to RabbitMQ via an action in the business partner window. Once initially send, they will from there onwards be automatically send whenever changed in metasfresh.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSyncBPartnersToRabbitMQ (boolean IsSyncBPartnersToRabbitMQ);

	/**
	 * Get IsSyncBPartnersToRabbitMQ.
	 * If checked, then business selected partners can be initially send to RabbitMQ via an action in the business partner window. Once initially send, they will from there onwards be automatically send whenever changed in metasfresh.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSyncBPartnersToRabbitMQ();

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, Object> COLUMN_IsSyncBPartnersToRabbitMQ = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "IsSyncBPartnersToRabbitMQ", null);
	String COLUMNNAME_IsSyncBPartnersToRabbitMQ = "IsSyncBPartnersToRabbitMQ";

	/**
	 * Set Send partner references.
	 * If ticked, then selected external references to business partners can be initially sent to RabbitMQ with an action in the External Reference window. After they have been sent once, they are automatically resent when changes are made.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSyncExternalReferencesToRabbitMQ (boolean IsSyncExternalReferencesToRabbitMQ);

	/**
	 * Get Send partner references.
	 * If ticked, then selected external references to business partners can be initially sent to RabbitMQ with an action in the External Reference window. After they have been sent once, they are automatically resent when changes are made.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSyncExternalReferencesToRabbitMQ();

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, Object> COLUMN_IsSyncExternalReferencesToRabbitMQ = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "IsSyncExternalReferencesToRabbitMQ", null);
	String COLUMNNAME_IsSyncExternalReferencesToRabbitMQ = "IsSyncExternalReferencesToRabbitMQ";

	/**
	 * Set Remote-URL.
	 * URL of the RabbitMQ HTTP API server. metasfresh will append the path "/messages/publish" to the given URL
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRemoteURL (String RemoteURL);

	/**
	 * Get Remote-URL.
	 * URL of the RabbitMQ HTTP API server. metasfresh will append the path "/messages/publish" to the given URL
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getRemoteURL();

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, Object> COLUMN_RemoteURL = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "RemoteURL", null);
	String COLUMNNAME_RemoteURL = "RemoteURL";

	/**
	 * Set Routing-Key.
	 * Messages are send to the default exchange with the given "routing_key". We assume that RabbitMQ contains a queue with the routing_key's name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRouting_Key (String Routing_Key);

	/**
	 * Get Routing-Key.
	 * Messages are send to the default exchange with the given "routing_key". We assume that RabbitMQ contains a queue with the routing_key's name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getRouting_Key();

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, Object> COLUMN_Routing_Key = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "Routing_Key", null);
	String COLUMNNAME_Routing_Key = "Routing_Key";

	/**
	 * Set AuthToken.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAuthToken(String AuthToken);

	/**
	 * Get AuthToken.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getAuthToken();

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, Object> COLUMN_AuthToken = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "AuthToken", null);
	String COLUMNNAME_AuthToken = "AuthToken";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Generated Interface for ProductType_External_Mapping
	 *  @author metasfresh (generated)
	 */
	@SuppressWarnings("unused")
	interface I_ProductType_External_Mapping
	{

		String Table_Name = "ProductType_External_Mapping";

	//	/** AD_Table_ID=542265 */
	//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


		/**
		 * Get Client.
		 * Client/Tenant for this installation.
		 *
		 * <br>Type: TableDir
		 * <br>Mandatory: true
		 * <br>Virtual Column: false
		 */
		int getAD_Client_ID();

		String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

		/**
		 * Set Organisation.
		 * Organisational entity within client
		 *
		 * <br>Type: Search
		 * <br>Mandatory: true
		 * <br>Virtual Column: false
		 */
		void setAD_Org_ID (int AD_Org_ID);

		/**
		 * Get Organisation.
		 * Organisational entity within client
		 *
		 * <br>Type: Search
		 * <br>Mandatory: true
		 * <br>Virtual Column: false
		 */
		int getAD_Org_ID();

		String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

		/**
		 * Get Created.
		 * Date this record was created
		 *
		 * <br>Type: DateTime
		 * <br>Mandatory: true
		 * <br>Virtual Column: false
		 */
		java.sql.Timestamp getCreated();

		ModelColumn<I_ProductType_External_Mapping, Object> COLUMN_Created = new ModelColumn<>(I_ProductType_External_Mapping.class, "Created", null);
		String COLUMNNAME_Created = "Created";

		/**
		 * Get Created By.
		 * User who created this records
		 *
		 * <br>Type: Table
		 * <br>Mandatory: true
		 * <br>Virtual Column: false
		 */
		int getCreatedBy();

		String COLUMNNAME_CreatedBy = "CreatedBy";

		/**
		 * Set External System Config.
		 *
		 * <br>Type: TableDir
		 * <br>Mandatory: false
		 * <br>Virtual Column: false
		 */
		void setExternalSystem_Config_ID (int ExternalSystem_Config_ID);

		/**
		 * Get External System Config.
		 *
		 * <br>Type: TableDir
		 * <br>Mandatory: false
		 * <br>Virtual Column: false
		 */
		int getExternalSystem_Config_ID();

		ModelColumn<I_ProductType_External_Mapping, Object> COLUMN_ExternalSystem_Config_ID = new ModelColumn<>(I_ProductType_External_Mapping.class, "ExternalSystem_Config_ID", null);
		String COLUMNNAME_ExternalSystem_Config_ID = "ExternalSystem_Config_ID";

		/**
		 * Set External value.
		 *
		 * <br>Type: String
		 * <br>Mandatory: true
		 * <br>Virtual Column: false
		 */
		void setExternalValue (String ExternalValue);

		/**
		 * Get External value.
		 *
		 * <br>Type: String
		 * <br>Mandatory: true
		 * <br>Virtual Column: false
		 */
		String getExternalValue();

		ModelColumn<I_ProductType_External_Mapping, Object> COLUMN_ExternalValue = new ModelColumn<>(I_ProductType_External_Mapping.class, "ExternalValue", null);
		String COLUMNNAME_ExternalValue = "ExternalValue";

		/**
		 * Set Active.
		 * The record is active in the system
		 *
		 * <br>Type: YesNo
		 * <br>Mandatory: true
		 * <br>Virtual Column: false
		 */
		void setIsActive (boolean IsActive);

		/**
		 * Get Active.
		 * The record is active in the system
		 *
		 * <br>Type: YesNo
		 * <br>Mandatory: true
		 * <br>Virtual Column: false
		 */
		boolean isActive();

		ModelColumn<I_ProductType_External_Mapping, Object> COLUMN_IsActive = new ModelColumn<>(I_ProductType_External_Mapping.class, "IsActive", null);
		String COLUMNNAME_IsActive = "IsActive";

		/**
		 * Set ProductType_External_Mapping.
		 *
		 * <br>Type: ID
		 * <br>Mandatory: true
		 * <br>Virtual Column: false
		 */
		void setProductType_External_Mapping_ID (int ProductType_External_Mapping_ID);

		/**
		 * Get ProductType_External_Mapping.
		 *
		 * <br>Type: ID
		 * <br>Mandatory: true
		 * <br>Virtual Column: false
		 */
		int getProductType_External_Mapping_ID();

		ModelColumn<I_ProductType_External_Mapping, Object> COLUMN_ProductType_External_Mapping_ID = new ModelColumn<>(I_ProductType_External_Mapping.class, "ProductType_External_Mapping_ID", null);
		String COLUMNNAME_ProductType_External_Mapping_ID = "ProductType_External_Mapping_ID";

		/**
		 * Get Updated.
		 * Date this record was updated
		 *
		 * <br>Type: DateTime
		 * <br>Mandatory: true
		 * <br>Virtual Column: false
		 */
		java.sql.Timestamp getUpdated();

		ModelColumn<I_ProductType_External_Mapping, Object> COLUMN_Updated = new ModelColumn<>(I_ProductType_External_Mapping.class, "Updated", null);
		String COLUMNNAME_Updated = "Updated";

		/**
		 * Get Updated By.
		 * User who updated this records
		 *
		 * <br>Type: Table
		 * <br>Mandatory: true
		 * <br>Virtual Column: false
		 */
		int getUpdatedBy();

		String COLUMNNAME_UpdatedBy = "UpdatedBy";

		/**
		 * Set Search Key.
		 * Search key for the record in the format required - must be unique
		 *
		 * <br>Type: String
		 * <br>Mandatory: true
		 * <br>Virtual Column: false
		 */
		void setValue (String Value);

		/**
		 * Get Search Key.
		 * Search key for the record in the format required - must be unique
		 *
		 * <br>Type: String
		 * <br>Mandatory: true
		 * <br>Virtual Column: false
		 */
		String getValue();

		ModelColumn<I_ProductType_External_Mapping, Object> COLUMN_Value = new ModelColumn<>(I_ProductType_External_Mapping.class, "Value", null);
		String COLUMNNAME_Value = "Value";
	}

	/** Generated Model for ProductType_External_Mapping
	 *  @author metasfresh (generated)
	 */
	@SuppressWarnings("unused")
	class X_ProductType_External_Mapping extends org.compiere.model.PO implements I_ProductType_External_Mapping, org.compiere.model.I_Persistent
	{

		private static final long serialVersionUID = -1673376520L;

		/** Standard Constructor */
		public X_ProductType_External_Mapping (final Properties ctx, final int ProductType_External_Mapping_ID, @Nullable final String trxName)
		{
		  super (ctx, ProductType_External_Mapping_ID, trxName);
		}

		/** Load Constructor */
		public X_ProductType_External_Mapping (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
		{
		  super (ctx, rs, trxName);
		}


		/** Load Meta Data */
		@Override
		protected org.compiere.model.POInfo initPO(final Properties ctx)
		{
			return org.compiere.model.POInfo.getPOInfo(Table_Name);
		}

		@Override
		public void setExternalSystem_Config_ID (final int ExternalSystem_Config_ID)
		{
			if (ExternalSystem_Config_ID < 1)
				set_Value (COLUMNNAME_ExternalSystem_Config_ID, null);
			else
				set_Value (COLUMNNAME_ExternalSystem_Config_ID, ExternalSystem_Config_ID);
		}

		@Override
		public int getExternalSystem_Config_ID()
		{
			return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_ID);
		}

		@Override
		public void setExternalValue (final String ExternalValue)
		{
			set_Value (COLUMNNAME_ExternalValue, ExternalValue);
		}

		@Override
		public String getExternalValue()
		{
			return get_ValueAsString(COLUMNNAME_ExternalValue);
		}

		@Override
		public void setProductType_External_Mapping_ID (final int ProductType_External_Mapping_ID)
		{
			if (ProductType_External_Mapping_ID < 1)
				set_ValueNoCheck (COLUMNNAME_ProductType_External_Mapping_ID, null);
			else
				set_ValueNoCheck (COLUMNNAME_ProductType_External_Mapping_ID, ProductType_External_Mapping_ID);
		}

		@Override
		public int getProductType_External_Mapping_ID()
		{
			return get_ValueAsInt(COLUMNNAME_ProductType_External_Mapping_ID);
		}

		@Override
		public void setValue (final String Value)
		{
			set_Value (COLUMNNAME_Value, Value);
		}

		@Override
		public String getValue()
		{
			return get_ValueAsString(COLUMNNAME_Value);
		}
	}
}
