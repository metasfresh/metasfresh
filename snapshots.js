module.exports = {
  "__version": "3.1.5",
  "purchase - vendor spec": {
    "Create a vendor with two contacts": {
      "1": [
        {
          "windowId": "123",
          "fieldsByName": {
            "AD_Client_ID": {
              "field": "AD_Client_ID",
              "value": {
                "key": "1000000",
                "caption": "metasfresh"
              },
              "readonly": false,
              "mandatory": true,
              "displayed": true,
              "lookupValuesStale": true,
              "widgetType": "List",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "AD_Client_ID"
              },
              "viewEditorRenderMode": "always"
            },
            "AD_Org_ID": {
              "field": "AD_Org_ID",
              "value": {
                "key": "1000000",
                "caption": "metasfresh AG"
              },
              "readonly": false,
              "mandatory": true,
              "displayed": true,
              "lookupValuesStale": true,
              "widgetType": "List",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "AD_Org_ID"
              },
              "viewEditorRenderMode": "always"
            },
            "Description": {
              "field": "Description",
              "value": null,
              "readonly": false,
              "mandatory": false,
              "displayed": true,
              "widgetType": "Text",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "Description"
              },
              "viewEditorRenderMode": "always"
            },
            "IsActive": {
              "field": "IsActive",
              "value": true,
              "readonly": false,
              "mandatory": true,
              "displayed": true,
              "widgetType": "Switch",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "IsActive"
              },
              "viewEditorRenderMode": "always"
            },
            "IsProducerAllotment": {
              "field": "IsProducerAllotment",
              "value": false,
              "readonly": false,
              "mandatory": false,
              "displayed": true,
              "widgetType": "YesNo",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "IsProducerAllotment"
              },
              "viewEditorRenderMode": "always"
            },
            "IsCompany": {
              "field": "IsCompany",
              "value": true,
              "readonly": false,
              "mandatory": false,
              "displayed": true,
              "widgetType": "YesNo",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "IsCompany"
              },
              "viewEditorRenderMode": "always"
            },
            "VATaxID": {
              "field": "VATaxID",
              "value": null,
              "readonly": false,
              "mandatory": false,
              "displayed": true,
              "widgetType": "Text",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "VATaxID"
              },
              "viewEditorRenderMode": "always"
            },
            "C_BP_Group_ID": {
              "field": "C_BP_Group_ID",
              "value": {
                "key": "1000000",
                "caption": "Standard"
              },
              "readonly": false,
              "mandatory": true,
              "displayed": true,
              "lookupValuesStale": true,
              "widgetType": "List",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "C_BP_Group_ID"
              },
              "viewEditorRenderMode": "always"
            },
            "AD_Language": {
              "field": "AD_Language",
              "value": {
                "key": "en_US",
                "caption": "English (US)"
              },
              "readonly": false,
              "mandatory": false,
              "displayed": true,
              "lookupValuesStale": true,
              "widgetType": "List",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "AD_Language"
              },
              "viewEditorRenderMode": "always"
            },
            "IsEdiRecipient": {
              "field": "IsEdiRecipient",
              "value": false,
              "readonly": false,
              "mandatory": true,
              "displayed": true,
              "widgetType": "YesNo",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "IsEdiRecipient"
              },
              "viewEditorRenderMode": "always"
            },
            "URL": {
              "field": "URL",
              "value": null,
              "readonly": false,
              "mandatory": false,
              "displayed": true,
              "widgetType": "Link",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "URL"
              },
              "viewEditorRenderMode": "always"
            },
            "IsReplicationLookupDefault": {
              "field": "IsReplicationLookupDefault",
              "value": false,
              "readonly": false,
              "mandatory": false,
              "displayed": true,
              "widgetType": "YesNo",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "IsReplicationLookupDefault"
              },
              "viewEditorRenderMode": "always"
            },
            "CreditLimitIndicator": {
              "field": "CreditLimitIndicator",
              "value": null,
              "readonly": true,
              "mandatory": false,
              "displayed": true,
              "widgetType": "Text",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "CreditLimitIndicator"
              },
              "viewEditorRenderMode": "never"
            },
            "Labels_548674": {
              "field": "Labels_548674",
              "value": {
                "values": []
              },
              "readonly": false,
              "mandatory": false,
              "displayed": true,
              "lookupValuesStale": true,
              "widgetType": "Labels",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "Labels_548674"
              },
              "viewEditorRenderMode": "always"
            },
            "Labels_554470": {
              "field": "Labels_554470",
              "value": {
                "values": []
              },
              "readonly": false,
              "mandatory": false,
              "displayed": true,
              "lookupValuesStale": true,
              "widgetType": "Labels",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "Labels_554470"
              },
              "viewEditorRenderMode": "always"
            }
          },
          "validStatus": {
            "valid": true
          },
          "saveStatus": {
            "deleted": false,
            "saved": true,
            "hasChanges": false,
            "error": false,
            "reason": "just loaded"
          },
          "includedTabsInfo": {
            "AD_Tab-222": {
              "tabid": "AD_Tab-222",
              "allowCreateNew": true,
              "allowDelete": true
            },
            "AD_Tab-223": {
              "tabid": "AD_Tab-223",
              "allowCreateNew": false,
              "allowDelete": true
            },
            "AD_Tab-224": {
              "tabid": "AD_Tab-224",
              "allowCreateNew": false,
              "allowDelete": true
            },
            "AD_Tab-226": {
              "tabid": "AD_Tab-226",
              "allowCreateNew": true,
              "allowDelete": true
            },
            "AD_Tab-496": {
              "tabid": "AD_Tab-496",
              "allowCreateNew": true,
              "allowDelete": true
            },
            "AD_Tab-540653": {
              "tabid": "AD_Tab-540653",
              "allowCreateNew": true,
              "allowDelete": true
            },
            "AD_Tab-540737": {
              "tabid": "AD_Tab-540737",
              "allowCreateNew": false,
              "allowCreateNewReason": "Tab is readonly",
              "allowDelete": false,
              "allowDeleteReason": "Tab is readonly"
            },
            "AD_Tab-540739": {
              "tabid": "AD_Tab-540739",
              "allowCreateNew": false,
              "allowDelete": true
            },
            "AD_Tab-540829": {
              "tabid": "AD_Tab-540829",
              "allowCreateNew": false,
              "allowCreateNewReason": "Tab is readonly",
              "allowDelete": false,
              "allowDeleteReason": "Tab is readonly"
            },
            "AD_Tab-540994": {
              "tabid": "AD_Tab-540994",
              "allowCreateNew": true,
              "allowDelete": true
            },
            "AD_Tab-541077": {
              "tabid": "AD_Tab-541077",
              "allowCreateNew": true,
              "allowDelete": true
            },
            "AD_Tab-541096": {
              "tabid": "AD_Tab-541096",
              "allowCreateNew": true,
              "allowDelete": true
            },
            "AD_Tab-541154": {
              "tabid": "AD_Tab-541154",
              "allowCreateNew": false,
              "allowDelete": true
            },
            "AD_Tab-541155": {
              "tabid": "AD_Tab-541155",
              "allowCreateNew": true,
              "allowDelete": true
            }
          },
          "standardActions": [
            "new",
            "advancedEdit",
            "email",
            "letter",
            "delete"
          ]
        }
      ]
    }
  },
  "New org test": {
    "Create a new org and bPartner and link them": {
      "1": [
        {
          "windowId": "123",
          "fieldsByName": {
            "AD_Client_ID": {
              "field": "AD_Client_ID",
              "value": {
                "key": "1000000",
                "caption": "metasfresh"
              },
              "readonly": false,
              "mandatory": true,
              "displayed": true,
              "lookupValuesStale": true,
              "widgetType": "List",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "AD_Client_ID"
              },
              "viewEditorRenderMode": "always"
            },
            "AD_Org_ID": {
              "field": "AD_Org_ID",
              "value": {
                "key": "0",
                "caption": "*",
                "description": "All Organizations"
              },
              "readonly": false,
              "mandatory": true,
              "displayed": true,
              "lookupValuesStale": true,
              "widgetType": "List",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "AD_Org_ID"
              },
              "viewEditorRenderMode": "always"
            },
            "Description": {
              "field": "Description",
              "value": null,
              "readonly": false,
              "mandatory": false,
              "displayed": true,
              "widgetType": "Text",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "Description"
              },
              "viewEditorRenderMode": "always"
            },
            "IsActive": {
              "field": "IsActive",
              "value": true,
              "readonly": false,
              "mandatory": true,
              "displayed": true,
              "widgetType": "Switch",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "IsActive"
              },
              "viewEditorRenderMode": "always"
            },
            "IsProducerAllotment": {
              "field": "IsProducerAllotment",
              "value": false,
              "readonly": false,
              "mandatory": false,
              "displayed": true,
              "widgetType": "YesNo",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "IsProducerAllotment"
              },
              "viewEditorRenderMode": "always"
            },
            "IsCompany": {
              "field": "IsCompany",
              "value": true,
              "readonly": false,
              "mandatory": false,
              "displayed": true,
              "widgetType": "YesNo",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "IsCompany"
              },
              "viewEditorRenderMode": "always"
            },
            "VATaxID": {
              "field": "VATaxID",
              "value": null,
              "readonly": false,
              "mandatory": false,
              "displayed": true,
              "widgetType": "Text",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "VATaxID"
              },
              "viewEditorRenderMode": "always"
            },
            "C_BP_Group_ID": {
              "field": "C_BP_Group_ID",
              "value": {
                "key": "1000000",
                "caption": "Standard"
              },
              "readonly": false,
              "mandatory": true,
              "displayed": true,
              "lookupValuesStale": true,
              "widgetType": "List",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "C_BP_Group_ID"
              },
              "viewEditorRenderMode": "always"
            },
            "AD_Language": {
              "field": "AD_Language",
              "value": {
                "key": "en_US",
                "caption": "English (US)"
              },
              "readonly": false,
              "mandatory": false,
              "displayed": true,
              "lookupValuesStale": true,
              "widgetType": "List",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "AD_Language"
              },
              "viewEditorRenderMode": "always"
            },
            "IsEdiRecipient": {
              "field": "IsEdiRecipient",
              "value": false,
              "readonly": false,
              "mandatory": true,
              "displayed": true,
              "widgetType": "YesNo",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "IsEdiRecipient"
              },
              "viewEditorRenderMode": "always"
            },
            "URL": {
              "field": "URL",
              "value": null,
              "readonly": false,
              "mandatory": false,
              "displayed": true,
              "widgetType": "Link",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "URL"
              },
              "viewEditorRenderMode": "always"
            },
            "IsReplicationLookupDefault": {
              "field": "IsReplicationLookupDefault",
              "value": false,
              "readonly": false,
              "mandatory": false,
              "displayed": true,
              "widgetType": "YesNo",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "IsReplicationLookupDefault"
              },
              "viewEditorRenderMode": "always"
            },
            "CreditLimitIndicator": {
              "field": "CreditLimitIndicator",
              "value": null,
              "readonly": true,
              "mandatory": false,
              "displayed": true,
              "widgetType": "Text",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "CreditLimitIndicator"
              },
              "viewEditorRenderMode": "never"
            },
            "Labels_548674": {
              "field": "Labels_548674",
              "value": {
                "values": []
              },
              "readonly": false,
              "mandatory": false,
              "displayed": true,
              "lookupValuesStale": true,
              "widgetType": "Labels",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "Labels_548674"
              },
              "viewEditorRenderMode": "always"
            },
            "Labels_554470": {
              "field": "Labels_554470",
              "value": {
                "values": []
              },
              "readonly": false,
              "mandatory": false,
              "displayed": true,
              "lookupValuesStale": true,
              "widgetType": "Labels",
              "validStatus": {
                "valid": true,
                "initialValue": true,
                "fieldName": "Labels_554470"
              },
              "viewEditorRenderMode": "always"
            }
          },
          "validStatus": {
            "valid": true
          },
          "saveStatus": {
            "deleted": false,
            "saved": true,
            "hasChanges": false,
            "error": false,
            "reason": "just loaded"
          },
          "includedTabsInfo": {
            "222": {
              "tabid": "222",
              "allowCreateNew": true,
              "allowDelete": true
            },
            "223": {
              "tabid": "223",
              "allowCreateNew": false,
              "allowDelete": true
            },
            "224": {
              "tabid": "224",
              "allowCreateNew": false,
              "allowDelete": true
            },
            "226": {
              "tabid": "226",
              "allowCreateNew": true,
              "allowDelete": true
            },
            "496": {
              "tabid": "496",
              "allowCreateNew": true,
              "allowDelete": true
            },
            "540653": {
              "tabid": "540653",
              "allowCreateNew": true,
              "allowDelete": true
            },
            "540737": {
              "tabid": "540737",
              "allowCreateNew": false,
              "allowCreateNewReason": "Tab is readonly",
              "allowDelete": false,
              "allowDeleteReason": "Tab is readonly"
            },
            "540739": {
              "tabid": "540739",
              "allowCreateNew": false,
              "allowDelete": true
            },
            "540829": {
              "tabid": "540829",
              "allowCreateNew": false,
              "allowCreateNewReason": "Tab is readonly",
              "allowDelete": false,
              "allowDeleteReason": "Tab is readonly"
            },
            "540994": {
              "tabid": "540994",
              "allowCreateNew": true,
              "allowDelete": true
            },
            "541077": {
              "tabid": "541077",
              "allowCreateNew": true,
              "allowDelete": true
            },
            "541096": {
              "tabid": "541096",
              "allowCreateNew": true,
              "allowDelete": true
            },
            "541154": {
              "tabid": "541154",
              "allowCreateNew": false,
              "allowDelete": true
            },
            "541155": {
              "tabid": "541155",
              "allowCreateNew": true,
              "allowDelete": true
            }
          },
          "standardActions": [
            "new",
            "advancedEdit",
            "email",
            "letter",
            "delete"
          ]
        }
      ]
    }
  }
}
