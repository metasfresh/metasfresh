 /*
  * #%L
  * de.metas.procurement.base
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

 package de.metas.procurement.base.rabbitmq;

 import de.metas.Profiles;
 import de.metas.common.procurement.sync.Constants;
 import de.metas.common.procurement.sync.protocol.RequestToMetasfresh;
 import de.metas.common.procurement.sync.protocol.dto.SyncBPartner;
 import de.metas.common.procurement.sync.protocol.dto.SyncProduct;
 import de.metas.common.procurement.sync.protocol.request_to_metasfresh.GetAllBPartnersRequest;
 import de.metas.common.procurement.sync.protocol.request_to_metasfresh.GetAllProductsRequest;
 import de.metas.common.procurement.sync.protocol.request_to_metasfresh.GetInfoMessageRequest;
 import de.metas.common.procurement.sync.protocol.request_to_metasfresh.PutProductSuppliesRequest;
 import de.metas.common.procurement.sync.protocol.request_to_metasfresh.PutWeeklySupplyRequest;
 import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutBPartnersRequest;
 import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutInfoMessageRequest;
 import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutProductsRequest;
 import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutRfQChangeRequest;
 import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutUserChangedRequest;
 import de.metas.procurement.base.IServerSyncBL;
 import de.metas.rabbitmq.RabbitMQAuditEntry;
 import de.metas.rabbitmq.RabbitMQAuditService;
 import de.metas.rabbitmq.RabbitMQDirection;
 import de.metas.util.Services;
 import lombok.NonNull;
 import org.adempiere.exceptions.AdempiereException;
 import org.adempiere.util.net.NetUtils;
 import org.springframework.amqp.rabbit.annotation.EnableRabbit;
 import org.springframework.amqp.rabbit.annotation.RabbitListener;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.context.annotation.Profile;
 import org.springframework.messaging.handler.annotation.Payload;

 import java.util.List;

 @Configuration
 @EnableRabbit
 @Profile(Profiles.PROFILE_App)
 public class ReceiverFromProcurementWeb
 {
	 private final SenderToProcurementWeb senderToProcurementWebUI;
	 private final RabbitMQAuditService rabbitLogger;

	 public ReceiverFromProcurementWeb(
			 @NonNull final SenderToProcurementWeb senderToProcurementWebUI,
			 @NonNull final RabbitMQAuditService rabbitLogger)
	 {
		 this.senderToProcurementWebUI = senderToProcurementWebUI;
		 this.rabbitLogger = rabbitLogger;
	 }

	 @RabbitListener(queues = Constants.QUEUE_NAME_PW_TO_MF)
	 public void receiveMessage(@NonNull @Payload final RequestToMetasfresh requestToMetasfresh)
	 {
		 rabbitLogger.log(RabbitMQAuditEntry.builder()
								  .queueName(Constants.QUEUE_NAME_PW_TO_MF)
								  .direction(RabbitMQDirection.IN)
								  .content(requestToMetasfresh)
								  .eventUUID(requestToMetasfresh.getEventId())
								  .host(NetUtils.getLocalHost())
								  .build());

		 invokeServerSyncBL(requestToMetasfresh);
	 }

	 private void invokeServerSyncBL(@NonNull final RequestToMetasfresh requestToMetasfresh)
	 {
		 final IServerSyncBL serverSyncBL = Services.get(IServerSyncBL.class);
		 if (requestToMetasfresh instanceof PutWeeklySupplyRequest)
		 {
			 serverSyncBL.reportWeekSupply((PutWeeklySupplyRequest)requestToMetasfresh);
		 }
		 else if (requestToMetasfresh instanceof PutProductSuppliesRequest)
		 {
			 serverSyncBL.reportProductSupplies((PutProductSuppliesRequest)requestToMetasfresh);
		 }
		 else if (requestToMetasfresh instanceof PutRfQChangeRequest)
		 {
			 serverSyncBL.reportRfQChanges((PutRfQChangeRequest)requestToMetasfresh);
		 }
		 else if (requestToMetasfresh instanceof GetAllBPartnersRequest)
		 {
			 final List<SyncBPartner> allBPartners = serverSyncBL.getAllBPartners();
			 senderToProcurementWebUI.send(PutBPartnersRequest.builder()
												   .bpartners(allBPartners)
												   .relatedEventId(requestToMetasfresh.getEventId())
												   .build());
		 }
		 else if (requestToMetasfresh instanceof GetAllProductsRequest)
		 {
			 final List<SyncProduct> allProducts = serverSyncBL.getAllProducts();
			 senderToProcurementWebUI.send(PutProductsRequest.builder()
												   .products(allProducts)
												   .relatedEventId(requestToMetasfresh.getEventId())
												   .build());
		 }
		 else if (requestToMetasfresh instanceof GetInfoMessageRequest)
		 {
			 final String infoMessage = serverSyncBL.getInfoMessage();
			 senderToProcurementWebUI.send(PutInfoMessageRequest.builder()
												   .message(infoMessage)
												   .relatedEventId(requestToMetasfresh.getEventId())
												   .build());
		 }
		 else if (requestToMetasfresh instanceof PutUserChangedRequest)
		 {
			 serverSyncBL.reportUserChanged((PutUserChangedRequest)requestToMetasfresh);
		 }
		 else
		 {
			 throw new AdempiereException("Unsupported type " + requestToMetasfresh.getClass().getName())
					 .appendParametersToMessage()
					 .setParameter("requestToMetasfresh", requestToMetasfresh);
		 }
	 }
 }
