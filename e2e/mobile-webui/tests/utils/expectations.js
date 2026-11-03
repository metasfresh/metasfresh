import { expect } from '@playwright/test';
import { test } from '../../playwright.config';

export const expectClasses = async ({ locator, expectedClasses }) => await test.step(`Expect classes: ${expectedClasses}`, async () => {
    await expect(locator).toHaveCount(1);

    let expectedClassesArray;
    if (Array.isArray(expectedClasses)) {
        expectedClassesArray = expectedClasses;
    } else {
        expectedClassesArray = expectedClasses.split(/\s+/);
    }

    const actualClasses = (await locator.getAttribute('class'))?.split(/\s+/) ?? [];

    for (const expectedClass of expectedClassesArray) {
        expect(actualClasses).toContain(expectedClass);
    }
});
