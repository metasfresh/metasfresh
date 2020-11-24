/*
 * IMPORTANT: be sure not to do this right at the start of a spec; if in doubt, do an extra "cy.wait(1000)".
 * Otherwise you will likely get this error
 *   "TypeError: Cannot read property 'key' of undefined"

 * Because often times we have to test for different languagues and some values in our fixtures
 * may be language-dependent, we have to provide versions for different languages prepended with
 * `language key`, like de_DE. Then instead of directly fetching data from the fixtures json
 * we pass it through this function, which selects keys accordingly.
 * 
 * @example
 * cy.fixture('misc/misc_dictionary.json').then(miscDictionary => {
 *     const taxCategoryName = getLanguageSpecific(miscDictionary, 'defaultTaxCategory');
 * });
 * 
 */
const getLanguageSpecific = (data, key) => {
  getLanguageSpecificWorkaround();

  // noinspection JSUnresolvedVariable
  const checkArray = Array.isArray(Cypress.reduxStore.getState().appHandler.me.language);
  const lang = checkArray ? 'en_EN' : Cypress.reduxStore.getState().appHandler.me.language.key;

  if (lang !== 'en_US' && !checkArray) {
    key = `${lang}__${key}`;
  }

  return data[`${key}`];
};

/*
 * Wrap cypress request in a Promise and resolve it on response.
 *
 * WARNING: Retarded Cypress request can't catch errors
 */
const wrapRequest = req => {
  return new Promise((resolve, reject) => {
    req.then(response => {
      try {
        resolve(response.body.result ? response.body.result[0] : response.body.values);
      } catch (e) {
        reject(e);
      }
    });
  });
};

/*
 * Find object with certain caption in an array of elements
 */
const findByName = (dataArray, name) => {
  let dataObject = null;

  for (let i = 0; i < dataArray.length; i += 1) {
    const obj = dataArray[i];

    if (obj.caption.includes(name)) {
      dataObject = obj;

      break;
    }
  }

  return dataObject;
};

/**
 * Human readable date and time with millis!
 *
 * The returned time format is: `15T11_32_17_211` `([day]T[HH]_[MM]_[SS]_[millis])`
 *
 * @returns {string}
 */
const humanReadableNow = () => {
  const date = new Date(Date.now() - new Date().getTimezoneOffset() * 60000).toISOString();
  // noinspection RegExpSingleCharAlternation
  return date
    .slice(8, date.length - 1)
    .split(/:|\./g)
    .join('_');
};

let date;
const appendHumanReadableNow = (str, dateOverride) => {
  if (dateOverride) {
    date = dateOverride;
  }
  if (!date) {
    date = humanReadableNow();
  }
  return `${str}_${date}`;
};

const getLanguageSpecificWorkaround_date = new Date();
const getLanguageSpecificWorkaround = () => {
  const TIME_TO_WAIT = 10 * 1000;
  return cy.get('body').then(function() {
    const now = new Date();
    const delta = now - getLanguageSpecificWorkaround_date;
    if (delta < TIME_TO_WAIT) {
      cy.log(`getLanguageSpecificWorkaround sleeping: date=${getLanguageSpecificWorkaround_date.getTime()}, now=${now.getTime()}, delta=${delta}ms`);
      // eslint-disable-next-line
      return cy.wait(5000);
    }
  });
};

export { getLanguageSpecific, wrapRequest, findByName, humanReadableNow, appendHumanReadableNow };
