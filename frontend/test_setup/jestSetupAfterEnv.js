import counterpart from 'counterpart';

afterEach(() => {
    cleanupTranslations();
});

const cleanupTranslations = () => {
    counterpart.registerTranslations('lang', {});
}
