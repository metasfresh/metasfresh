const fs = require('fs');
const axios = require('axios');
const path = require('path');

const filePath = path.join(__dirname, 'Bestelldaten.mit.Partner.NV.removed.txt'); // Update with your actual file path
const apiUrl = 'http://localhost:8282/app/api/v2/orders/sales/candidates/bulk';
const authToken = 'authToken'; // Update with your token
const BULK_SIZE = 4000; // Number of lines per bulk request

// Read the file
fs.readFile(filePath, 'utf8', async (err, data) => {
    if (err) {
        console.error('Error reading file:', err);
        return;
    }

    const lines = data.split('\n');
    const headers = lines[0].split('\t'); // Assuming tab-separated values
    const allOrders = [];

    for (let i = 1; i < lines.length; i++) {
        if (!lines[i].trim()) continue; // Skip empty lines
        const values = lines[i].split('\t');
        const orderObject = {};

        headers.forEach((header, index) => {
            orderObject[header] = values[index] || '';
        });

        allOrders.push({
            orgCode: '001',
            externalLineId: `externalLineId_${i}`,
            externalHeaderId: 'externalHeaderId_001',
            dataSource: 'int-DEST.de.metas.ordercandidate',
            dataDest: null,
            bpartner: {
                bpartnerIdentifier: `ext-Other-${orderObject['Partner No']}` || null,
                bpartnerLocationIdentifier: `ext-Other-${orderObject['delivery_street']}` || null
            },
            dateRequired: "2025-02-19",
            flatrateConditionsId: 0,
            orderDocType: 'SalesOrder',
            productIdentifier: `val-${orderObject['sku']}`,
            qty: parseInt(orderObject['quantity'], 10) || 1,
            price: parseFloat(orderObject['price']) || 0,
            currencyCode: orderObject['currency'] || 'EUR',
            discount: 0,
            poReference: orderObject['ordernumber'],
            warehouseCode: null,
            warehouseDestCode: null,
            dateCandidate: "2025-02-19",
            line: i,
            isManualPrice: true,
            isImportedWithIssues: false,
            deliveryViaRule: 'S',
            deliveryRule: 'A',
            description: 'Order from file',
            dateOrdered: "2025-02-19",
            importWarningMessage: ''
        });
    }

    // Split all orders into chunks of BULK_SIZE
    const bulkChunks = [];
    for (let i = 0; i < allOrders.length; i += BULK_SIZE) {
        bulkChunks.push(allOrders.slice(i, i + BULK_SIZE));
    }

    console.log(`Total orders: ${allOrders.length}, Processing in ${bulkChunks.length} bulk(s)...`);

    // Function to send bulk orders sequentially
    async function sendBulkOrders() {
        for (let index = 0; index < bulkChunks.length; index++) {
            const payload = { requests: bulkChunks[index] };

            try {
                const response = await axios.post(apiUrl, payload, {
                    headers: {
                        'Authorization': authToken,
                        'Content-Type': 'application/json'
                    }
                });
                console.log(`Bulk ${index + 1}/${bulkChunks.length} submitted successfully:`, response.data);
            } catch (error) {
                console.error(`Error submitting bulk ${index + 1}:`, error.response ? error.response.data : error.message);
            }
        }
    }

    // Start sending bulk orders
    await sendBulkOrders();
});