const fs = require('fs');
const axios = require('axios');
const path = require('path');

const filePath = path.join(__dirname, 'Partnerimport.Template.v4.21.10.2024.reduziert.1.txt'); // Update with your actual file path
const apiUrl = 'http://localhost:8282/api/v2/bpartner/001'; // Update with your actual endpoint
const authToken = 'authToken'; // Update with your token

let partnerCounter = 100000;
let locationCounter = 110000;

// Read the file
fs.readFile(filePath, 'utf8', async (err, data) => {
    if (err) {
        console.error('Error reading file:', err);
        return;
    }

    const lines = data.split('\n');
    const headers = lines[0].split('\t'); // Assuming tab-separated values
    const allPartners = [];

    for (let i = 1; i < lines.length; i++) {
        if (!lines[i].trim()) continue; // Skip empty lines
        const values = lines[i].split('\t');
        const partnerObject = {};

        headers.forEach((header, index) => {
            partnerObject[header] = values[index] || '';
        });

        allPartners.push({
			bpartnerIdentifier: `ext-Other-${partnerCounter.toString().padStart(3, '0')}`,
			externalReferenceUrl: 'www.ExternalReferenceURL.com',
            bpartnerComposite: {
                bpartner: {
                    code: partnerObject['Partner_Suchschlüssel'] || '',
                    name: partnerObject['Partner_Name'] || '',
                    companyName: partnerObject['Partner_Name 2'] || '',
                    parentId: null,
                    phone: null,
                    language: 'de',
                    url: partnerObject['Partner_Website'] || '',
                    group: partnerObject['Partner_Gruppe'] || ''
                },
                locations: {
                    requestItems: [
                        {
                            locationIdentifier: `gln-l${locationCounter++}`,
                            location: {
                                address1: partnerObject['Adresse_Straße und Nr.'] || '',
                                address2: partnerObject['Adresse_Zusatz'] || '',
                                poBox: null,
                                district: null,
                                region: null,
                                city: partnerObject['Adresse_Ort'] || '',
                                countryCode: 'DE',
                                gln: null,
                                postal: null
                            }
                        }
                    ]
                },
                contacts: {
                    requestItems: [
                        {
                            contactIdentifier: `ext-Other-${partnerCounter}`,
                            contact: {
                                code: `c${partnerCounter}`,
                                name: `${partnerObject['Kontakt_Vorname'] || ''} ${partnerObject['Kontakt_Nachname'] || ''}`.trim(),
                                email: partnerObject['Kontakt_Email'] || '',
                                fax: partnerObject['Kontakt_Fax'] || '',
                                invoiceEmailEnabled: false
                            }
                        }
                    ]
                }
            }
        });
		partnerCounter++;
    }

    console.log(`Total partners: ${allPartners.length}, Sending individually...`);

    // Function to send each partner sequentially
    async function sendPartnersSequentially() {
        for (let i = 0; i < allPartners.length; i++) {
            const payload = {
                requestItems: [allPartners[i]], // Send one partner at a time
                syncAdvise: {
                    ifNotExists: 'CREATE',
                    ifExists: 'UPDATE_MERGE'
                }
            };

            try {
                const response = await axios.put(apiUrl, payload, {
                    headers: {
                        'Authorization': authToken,
                        'Content-Type': 'application/json'
                    }
                });
                console.log(`Partner ${i + 1}/${allPartners.length} submitted successfully:`, response.data);
            } catch (error) {
                console.error(`Error submitting partner ${i + 1}:`, error.response ? error.response.data : error.message);
            }
        }
    }

    // Start sending partners one by one
    await sendPartnersSequentially();
});