import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';

var mock = new MockAdapter(axios);

// Mock any GET request to /users
// arguments for reply are (status, data, headers)
mock.onGet(`${window.config.API_URL}/userWorkflows/launchers`).reply(200, {
  "requestId": 1000027,
  "endpointResponse": {
    "launchers": [
      {
        "caption": "0003 | Test_Kunde1",
        "wfProviderId": "picking",
        "startedWFProcessId": "picking-fa75b6c0-a8a7-4b0d-a0d5-d6ded9a43148",
        "wfParameters": {}
      },
      {
        "caption": "0003 | Test_Kunde1",
        "wfProviderId": "picking",
        "startedWFProcessId": "picking-0e7bae3d-1b12-4f58-99bc-19c20cf19056",
        "wfParameters": {}
      },
      {
        "caption": "0003 | Test_Kunde1",
        "wfProviderId": "picking",
        "startedWFProcessId": "picking-a8c1a0a0-6fba-4b47-989b-b710f23b2083",
        "wfParameters": {}
      },
      {
        "caption": "0003 | Test_Kunde1",
        "wfProviderId": "picking",
        "startedWFProcessId": "picking-6d993e1c-fa09-4ce6-8b8a-8eca640ecfb1",
        "wfParameters": {}
      },
      {
        "caption": "0003 | Test_Kunde1",
        "wfProviderId": "picking",
        "startedWFProcessId": "picking-9b6e6773-a7ae-4155-8123-0f861f2320c5",
        "wfParameters": {}
      },
      {
        "caption": "0003 | Test_Kunde1",
        "wfProviderId": "picking",
        "startedWFProcessId": "picking-50d56d86-0884-4ece-84a4-27a160f27415",
        "wfParameters": {}
      },
      {
        "caption": "0004 | Test_Kunde1",
        "wfProviderId": "picking",
        "startedWFProcessId": null,
        "wfParameters": {
          "salesOrderId": 1000007,
          "customerId": 2155895,
          "customerLocationId": 2202691,
          "warehouseTypeId": null,
          "locked": false
        }
      },
      {
        "caption": "0005 | Test_Kunde1",
        "wfProviderId": "picking",
        "startedWFProcessId": null,
        "wfParameters": {
          "salesOrderId": 1000009,
          "customerId": 2155895,
          "customerLocationId": 2202691,
          "warehouseTypeId": null,
          "locked": false
        }
      },
      {
        "caption": "0006 | Test_Kunde1",
        "wfProviderId": "picking",
        "startedWFProcessId": null,
        "wfParameters": {
          "salesOrderId": 1000010,
          "customerId": 2155895,
          "customerLocationId": 2202691,
          "warehouseTypeId": null,
          "locked": false
        }
      },
      {
        "caption": "0007 | Test_Kunde1",
        "wfProviderId": "picking",
        "startedWFProcessId": null,
        "wfParameters": {
          "salesOrderId": 1000011,
          "customerId": 2155895,
          "customerLocationId": 2202691,
          "warehouseTypeId": null,
          "locked": false
        }
      },
      {
        "caption": "0008 | Test_Kunde1",
        "wfProviderId": "picking",
        "startedWFProcessId": null,
        "wfParameters": {
          "salesOrderId": 1000013,
          "customerId": 2155895,
          "customerLocationId": 2202691,
          "warehouseTypeId": null,
          "locked": false
        }
      },
      {
        "caption": "0009 | Test_Kunde1",
        "wfProviderId": "picking",
        "startedWFProcessId": null,
        "wfParameters": {
          "salesOrderId": 1000014,
          "customerId": 2155895,
          "customerLocationId": 2202691,
          "warehouseTypeId": null,
          "locked": false
        }
      },
      {
        "caption": "0010 | Test_Kunde1",
        "wfProviderId": "picking",
        "startedWFProcessId": null,
        "wfParameters": {
          "salesOrderId": 1000015,
          "customerId": 2155895,
          "customerLocationId": 2202691,
          "warehouseTypeId": null,
          "locked": false
        }
      },
      {
        "caption": "0011 | Test_Kunde1",
        "wfProviderId": "picking",
        "startedWFProcessId": null,
        "wfParameters": {
          "salesOrderId": 1000016,
          "customerId": 2155895,
          "customerLocationId": 2202691,
          "warehouseTypeId": null,
          "locked": false
        }
      },
      {
        "caption": "0013 | Test_Kunde1",
        "wfProviderId": "picking",
        "startedWFProcessId": null,
        "wfParameters": {
          "salesOrderId": 1000018,
          "customerId": 2155895,
          "customerLocationId": 2202691,
          "warehouseTypeId": null,
          "locked": false
        }
      }
    ]
  }
});

/**
 * @method getLaunchers
 * @summary Get the list of available launchers
 * @param {object} `token` - The token to use for authentication 
 * @returns 
 */
export function getLaunchers({ token }) {
  return axios.get(`${window.config.API_URL}/userWorkflows/launchers`, {
    headers: {
      'Authorization': token,
      'accept': 'application/json',
    }
  });
}
