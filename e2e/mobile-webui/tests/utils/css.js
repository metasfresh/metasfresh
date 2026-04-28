export const cssEscape = (value) => {
    if (value == null) {
        return '';
    }

    return String(value).replace(
        /([\x00-\x1F\x7F]|^-?[\d]|[^\w\-\u{00A0}-\u{10FFFF}])/gu,
        (char) => {
            if (char === '\0') {
                return '\uFFFD';
            }
            return char.length === 1
                ? `\\${char}`
                : `\\${char}`;
        }
    );
};
