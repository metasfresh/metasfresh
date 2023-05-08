-- 2022-11-16T13:59:17.005Z
/* DDL */

SELECT public.db_alter_table('M_Product', 'ALTER TABLE public.M_Product ADD COLUMN Grade VARCHAR(250)')
;

-- 2022-11-24T14:37:37.776Z
/* DDL */

CREATE TABLE public.M_Delivery_Planning
(
    ActualDeliveredQty         NUMERIC                                            NOT NULL,
    ActualDeliveryDate         TIMESTAMP WITHOUT TIME ZONE,
    ActualLoadingDate          TIMESTAMP WITHOUT TIME ZONE,
    ActualLoadQty              NUMERIC                                            NOT NULL,
    AD_Client_ID               NUMERIC(10)                                        NOT NULL,
    AD_Org_ID                  NUMERIC(10)                                        NOT NULL,
    Batch                      VARCHAR(250),
    C_BPartner_ID              NUMERIC(10),
    C_BPartner_Location_ID     NUMERIC(10),
    C_DestinationCountry_ID    NUMERIC(10),
    C_Incoterms_ID             NUMERIC(10),
    C_Order_ID                 NUMERIC(10),
    C_OrderLine_ID             NUMERIC(10),
    C_OriginCountry_ID         NUMERIC(10),
    Created                    TIMESTAMP WITH TIME ZONE                           NOT NULL,
    CreatedBy                  NUMERIC(10)                                        NOT NULL,
    C_UOM_ID                   NUMERIC(10),
    Forwarder                  VARCHAR(1000),
    IsActive                   CHAR(1) DEFAULT 'Y' CHECK (IsActive IN ('Y', 'N')) NOT NULL,
    IsB2B                      CHAR(1) DEFAULT 'N' CHECK (IsB2B IN ('Y', 'N'))    NOT NULL,
    M_Delivery_Planning_ID     NUMERIC(10)                                        NOT NULL,
    M_Delivery_Planning_Type   VARCHAR(250),
    MeansOfTransportation      VARCHAR(250),
    M_Product_ID               NUMERIC(10),
    M_ReceiptSchedule_ID       NUMERIC(10),
    M_SectionCode_ID           NUMERIC(10),
    M_ShipmentSchedule_ID      NUMERIC(10),
    M_ShipperTransportation_ID NUMERIC(10),
    M_Warehouse_ID             NUMERIC(10),
    OrderStatus                VARCHAR(250),
    PlannedDeliveryDate        TIMESTAMP WITHOUT TIME ZONE,
    PlannedLoadingDate         TIMESTAMP WITHOUT TIME ZONE,
    QtyOrdered                 NUMERIC                                            NOT NULL,
    QtyTotalOpen               NUMERIC                                            NOT NULL,
    ReleaseNo                  VARCHAR(250),
    TransportDetails           VARCHAR(5000),
    Updated                    TIMESTAMP WITH TIME ZONE                           NOT NULL,
    UpdatedBy                  NUMERIC(10)                                        NOT NULL,
    WayBillNo                  VARCHAR(15),
    CONSTRAINT CBPartner_MDeliveryPlanning FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT CBPartnerLocation_MDeliveryPlanning FOREIGN KEY (C_BPartner_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT CDestinationCountry_MDeliveryPlanning FOREIGN KEY (C_DestinationCountry_ID) REFERENCES public.C_Country DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT CIncoterms_MDeliveryPlanning FOREIGN KEY (C_Incoterms_ID) REFERENCES public.C_Incoterms DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT COrder_MDeliveryPlanning FOREIGN KEY (C_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT COrderLine_MDeliveryPlanning FOREIGN KEY (C_OrderLine_ID) REFERENCES public.C_OrderLine DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT COriginCountry_MDeliveryPlanning FOREIGN KEY (C_OriginCountry_ID) REFERENCES public.C_Country DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT CUOM_MDeliveryPlanning FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT M_Delivery_Planning_Key PRIMARY KEY (M_Delivery_Planning_ID),
    CONSTRAINT MProduct_MDeliveryPlanning FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT MReceiptSchedule_MDeliveryPlanning FOREIGN KEY (M_ReceiptSchedule_ID) REFERENCES public.M_ReceiptSchedule DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT MSectionCode_MDeliveryPlanning FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT MShipmentSchedule_MDeliveryPlanning FOREIGN KEY (M_ShipmentSchedule_ID) REFERENCES public.M_ShipmentSchedule DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT MShipperTransportation_MDeliveryPlanning FOREIGN KEY (M_ShipperTransportation_ID) REFERENCES public.M_ShipperTransportation DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT MWarehouse_MDeliveryPlanning FOREIGN KEY (M_Warehouse_ID) REFERENCES public.M_Warehouse DEFERRABLE INITIALLY DEFERRED
)
;

