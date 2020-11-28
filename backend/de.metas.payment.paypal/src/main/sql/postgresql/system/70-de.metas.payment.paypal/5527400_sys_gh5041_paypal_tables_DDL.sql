drop table if exists PayPal_Log;
drop table if exists PayPal_Config;
drop table if exists PayPal_Order;
drop table if exists C_Payment_Reservation_Capture;
drop table if exists C_Payment_Reservation;

-- 2019-07-17T19:46:20.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.C_Payment_Reservation (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Amount NUMERIC NOT NULL, Bill_BPartner_ID NUMERIC(10) NOT NULL, Bill_EMail VARCHAR(255) NOT NULL, Bill_User_ID NUMERIC(10) NOT NULL, C_Currency_ID NUMERIC(10) NOT NULL, C_Order_ID NUMERIC(10) NOT NULL, C_Payment_Reservation_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DateTrx TIMESTAMP WITHOUT TIME ZONE NOT NULL, DocStatus VARCHAR(10), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, PaymentRule CHAR(1) NOT NULL, Processed CHAR(1) DEFAULT 'N' CHECK (Processed IN ('Y','N')) NOT NULL, Status CHAR(1) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT BillBPartner_CPaymentReservation FOREIGN KEY (Bill_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED, CONSTRAINT BillUser_CPaymentReservation FOREIGN KEY (Bill_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CCurrency_CPaymentReservation FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED, CONSTRAINT COrder_CPaymentReservation FOREIGN KEY (C_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_Payment_Reservation_Key PRIMARY KEY (C_Payment_Reservation_ID))
;

-- 2019-07-18T06:59:03.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.C_Payment_Reservation_Capture (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Amount NUMERIC NOT NULL, C_Payment_Reservation_Capture_ID NUMERIC(10) NOT NULL, C_Payment_Reservation_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Status VARCHAR(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_Payment_Reservation_Capture_Key PRIMARY KEY (C_Payment_Reservation_Capture_ID), CONSTRAINT CPaymentReservation_CPaymentReservationCapture FOREIGN KEY (C_Payment_Reservation_ID) REFERENCES public.C_Payment_Reservation DEFERRABLE INITIALLY DEFERRED)
;

-- 2019-07-18T06:43:47.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.PayPal_Config (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description VARCHAR(2000), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, PayPal_BaseUrl VARCHAR(255), PayPal_ClientId VARCHAR(255) NOT NULL, PayPal_ClientSecret VARCHAR(255) NOT NULL, PayPal_Config_ID NUMERIC(10) NOT NULL, PayPal_PayerApprovalRequest_MailTemplate_ID NUMERIC(10) NOT NULL, PayPal_PaymentApprovedCallbackUrl VARCHAR(255) NOT NULL, PayPal_Sandbox CHAR(1) DEFAULT 'N' CHECK (PayPal_Sandbox IN ('Y','N')) NOT NULL, PayPal_WebUrl VARCHAR(255), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT PayPal_Config_Key PRIMARY KEY (PayPal_Config_ID), CONSTRAINT PayPalPayerApprovalRequestMailTemplate_PayPalConfig FOREIGN KEY (PayPal_PayerApprovalRequest_MailTemplate_ID) REFERENCES public.R_MailText DEFERRABLE INITIALLY DEFERRED)
;

-- 2019-07-19T07:26:08.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.PayPal_Order (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Payment_Reservation_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, ExternalId VARCHAR(40), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, PayPal_AuthorizationId VARCHAR(40), PayPal_Order_ID NUMERIC(10) NOT NULL, PayPal_OrderJSON TEXT DEFAULT '0', PayPal_PayerApproveUrl VARCHAR(255), Status VARCHAR(20) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CPaymentReservation_PayPalOrder FOREIGN KEY (C_Payment_Reservation_ID) REFERENCES public.C_Payment_Reservation DEFERRABLE INITIALLY DEFERRED, CONSTRAINT PayPal_Order_Key PRIMARY KEY (PayPal_Order_ID))
;

-- 2019-07-19T06:46:29.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.PayPal_Log (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Order_ID NUMERIC(10), C_Payment_Reservation_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, PayPal_Log_ID NUMERIC(10) NOT NULL, PayPal_Order_ID NUMERIC(10), RequestBody TEXT, RequestHeaders TEXT, RequestMethod VARCHAR(40), RequestPath VARCHAR(2000), ResponseBody TEXT, ResponseCode NUMERIC(10), ResponseHeaders TEXT, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT PayPal_Log_Key PRIMARY KEY (PayPal_Log_ID))
;

