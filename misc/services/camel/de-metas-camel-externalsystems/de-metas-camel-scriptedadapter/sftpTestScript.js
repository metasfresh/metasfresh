function transform(messageFromMetasfresh) {
	var input = JSON.parse(messageFromMetasfresh);
	var result = { status: "transformed", original: input.value };
	return JSON.stringify(result);
}
