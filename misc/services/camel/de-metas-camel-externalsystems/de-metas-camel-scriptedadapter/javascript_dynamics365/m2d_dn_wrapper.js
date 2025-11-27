function transform(messageFromMetasfresh) {
    try {
        const inputJson = JSON.parse(messageFromMetasfresh);
        const result = { m2d_dn: [inputJson] };
        return JSON.stringify(result);
    } catch (err) {
        return JSON.stringify({ error: "Invalid JSON input", details: err.message });
    }
}
