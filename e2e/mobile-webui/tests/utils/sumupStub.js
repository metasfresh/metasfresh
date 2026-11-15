import { randomUUID } from 'crypto';
import { expect } from '@playwright/test';
import { page } from './common';

/**
 * SumUp API stub on the stack's WireMock, for the POS card settlement specs.
 *
 * The app server talks to SumUp via the `sumup.baseUrl` property: the E2E stack sets it to
 * `http://wiremock:8080/sumup/v0.1` (docker-builds/e2e-tests/compose.yml); a local run starts the app
 * server with `-Dsumup.baseUrl=<WIREMOCK_BASE_URL>/sumup/v0.1`. `WIREMOCK_BASE_URL` is the address the
 * spec registers stubs at, the same variable the picking spec uses.
 *
 * Stubs are registered through the admin API, never committed as mapping files, so a missing WireMock
 * fails the spec at setup instead of silently exercising an unstubbed SumUp. WireMock state is global,
 * so a spec using this module runs serially and calls {@link SumUpStub.removeAll} after each test.
 *
 * Flow covered:
 * - the reader checkout answers a `client_transaction_id` made of a UUID fixed per checkout stub plus
 *   the checkout's `description` (the card settlement id, see POSInvoiceCardSettlementService#send),
 *   echoed by the response template; so each checkout gets its own id, and the spec knows it as soon
 *   as it knows the settlement id;
 * - the transaction lookup (`GET /me/transactions?client_transaction_id=…`, polled by the app server's
 *   SumUp poller and by the till's check) answers the status the spec sets; the state is flipped by
 *   re-registering that mapping;
 * - the reader terminate call answers 204 (SumUp does not confirm it).
 */
export const WIREMOCK_BASE_URL = process.env.WIREMOCK_BASE_URL || 'http://localhost:18080';
const SUMUP_PATH = '/sumup/v0.1';

let registeredStubIds = [];
const transactionStubIdsByClientTransactionId = new Map();

const registerStub = async (stub) => {
    const response = await page.request.post(`${WIREMOCK_BASE_URL}/__admin/mappings`, {
        data: stub,
        headers: { 'Content-Type': 'application/json' },
    });
    if (!response.ok()) {
        throw new Error(`Failed to register WireMock stub at ${WIREMOCK_BASE_URL}: HTTP ${response.status()} ${response.statusText()}`);
    }
    const body = await response.json();
    // recorded here, not by the caller: a throw between two registrations must still leave the first one removable
    registeredStubIds.push(body.id);
    return body.id;
};

const removeStub = async (stubId) => {
    if (!stubId) return;
    registeredStubIds = registeredStubIds.filter((id) => id !== stubId);
    const response = await page.request.delete(`${WIREMOCK_BASE_URL}/__admin/mappings/${stubId}`);
    if (!response.ok() && response.status() !== 404) {
        throw new Error(`Failed to remove WireMock stub ${stubId}: HTTP ${response.status()}`);
    }
};

/** Number of `GET /me/transactions?client_transaction_id=<clientTransactionId>` requests WireMock received so far. */
const countTransactionLookups = async ({ clientTransactionId }) => {
    const response = await page.request.post(`${WIREMOCK_BASE_URL}/__admin/requests/count`, {
        data: {
            method: 'GET',
            urlPath: `${SUMUP_PATH}/me/transactions`,
            queryParameters: { client_transaction_id: { equalTo: clientTransactionId } },
        },
        headers: { 'Content-Type': 'application/json' },
    });
    if (!response.ok()) {
        throw new Error(`Failed to count WireMock requests: HTTP ${response.status()}`);
    }
    return (await response.json()).count;
};

const readerPath = ({ merchantCode, readerId }) => `${SUMUP_PATH}/merchants/${merchantCode}/readers/${readerId}`;

export const SumUpStub = {
    /**
     * Stubs the reader checkout of the given merchant / reader.
     * @param fixedDelayMilliseconds delays the answer, e.g. beyond the client's 60 s read timeout to make the send indeterminate
     * @returns {{ clientTransactionIdOf: function(settlementId): string }} maps a settlement id to the `client_transaction_id` this stub answers for it
     */
    stubCheckout: async ({ merchantCode, readerId, fixedDelayMilliseconds }) => {
        const prefix = randomUUID();
        await registerStub({
            request: {
                method: 'POST',
                urlPath: `${readerPath({ merchantCode, readerId })}/checkout`,
            },
            response: {
                status: 201,
                headers: { 'Content-Type': 'application/json' },
                body: `{"data":{"client_transaction_id":"${prefix}-{{jsonPath request.body '$.description'}}"}}`,
                transformers: ['response-template'],
                ...(fixedDelayMilliseconds ? { fixedDelayMilliseconds } : {}),
            },
        });

        return { clientTransactionIdOf: (settlementId) => `${prefix}-${settlementId}` };
    },

    /**
     * Stubs the reader checkout of a till SALE (as opposed to an invoice settlement): the sale's checkout carries no
     * `description`, so the answer cannot echo one and uses a fixed UUID `client_transaction_id`, known to the spec
     * as soon as the stub is registered. One sale checkout stub per test: a second one would answer the same id.
     * @param fixedDelayMilliseconds delays the answer, e.g. beyond the client's 60 s read timeout to make the send indeterminate
     * @returns {Promise<{ clientTransactionId: string }>}
     */
    stubSaleCheckout: async ({ merchantCode, readerId, fixedDelayMilliseconds }) => {
        const clientTransactionId = randomUUID();
        await registerStub({
            request: {
                method: 'POST',
                urlPath: `${readerPath({ merchantCode, readerId })}/checkout`,
            },
            response: {
                status: 201,
                headers: { 'Content-Type': 'application/json' },
                jsonBody: { data: { client_transaction_id: clientTransactionId } },
                ...(fixedDelayMilliseconds ? { fixedDelayMilliseconds } : {}),
            },
        });

        return { clientTransactionId };
    },

    /**
     * Sets what SumUp answers for the transaction: `PENDING`, `SUCCESSFUL`, `FAILED` or `CANCELLED`.
     * Replaces the answer set before for the same transaction.
     * @returns {Promise<{ waitForLookup: function(): Promise<void> }>} `waitForLookup` waits until the app server
     *  asked SumUp for this transaction after this call, i.e. it got the new answer
     */
    setTransactionStatus: async ({ clientTransactionId, status, amount, currencyCode = 'EUR', merchantCode }) => {
        await removeStub(transactionStubIdsByClientTransactionId.get(clientTransactionId));
        transactionStubIdsByClientTransactionId.delete(clientTransactionId);

        const stubId = await registerStub({
            request: {
                method: 'GET',
                urlPath: `${SUMUP_PATH}/me/transactions`,
                queryParameters: { client_transaction_id: { equalTo: clientTransactionId } },
            },
            response: {
                status: 200,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    id: `TX-${clientTransactionId}`,
                    client_transaction_id: '{{request.query.client_transaction_id}}',
                    merchant_code: merchantCode,
                    timestamp: '{{now}}',
                    status,
                    amount: Number(amount),
                    currency: currencyCode,
                    card: { type: 'VISA', last_4_digits: '0000' },
                }),
                transformers: ['response-template'],
            },
        });
        transactionStubIdsByClientTransactionId.set(clientTransactionId, stubId);

        const lookupCountBefore = await countTransactionLookups({ clientTransactionId });
        return {
            waitForLookup: async ({ timeout = 60_000 } = {}) => {
                await expect.poll(() => countTransactionLookups({ clientTransactionId }), { timeout, intervals: [1_000] })
                    .toBeGreaterThan(lookupCountBefore);
            },
        };
    },

    /** Stubs the reader's terminate call: SumUp answers 204 and processes it asynchronously. */
    stubTerminate: async ({ merchantCode, readerId }) => {
        await registerStub({
            request: {
                method: 'POST',
                urlPath: `${readerPath({ merchantCode, readerId })}/terminate`,
            },
            response: { status: 204 },
        });
    },

    /** Removes every stub this module registered; call it after each test. */
    removeAll: async () => {
        const stubIds = [...registeredStubIds];
        transactionStubIdsByClientTransactionId.clear();
        for (const stubId of stubIds) {
            await removeStub(stubId);
        }
    },
};
