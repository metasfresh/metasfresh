export const removeZeroEntriesResourcesFromArray = (resources) => {
  const resourcesFiltered = toTree(resources)
    .flatMap((root) => removeZeroEntriesFromNode(root))
    .filter((root) => root != null)
    .flatMap((root) => convertNodeToResourcesArray(root));

  return resourcesFiltered.length !== resources.length
    ? resourcesFiltered
    : resources;
};

const toTree = (resourcesArray) => {
  if (!resourcesArray) {
    return [];
  }

  const nodesById = {};
  resourcesArray.forEach((resource) => {
    nodesById[resource.id] = {
      resource,
      entriesCount: resource.entriesCount ?? 0,
      children: [],
    };
  });

  const roots = [];
  resourcesArray.forEach((resource) => {
    const node = nodesById[resource.id];
    const parentNode = resource.parentId ? nodesById[resource.parentId] : null;

    if (!parentNode) {
      roots.push(node);
    } else {
      parentNode.children.push(node);
    }
  });

  return roots;
};

const removeZeroEntriesFromNode = (node) => {
  node.children = node.children
    .map((child) => removeZeroEntriesFromNode(child))
    .filter((child) => child != null);

  return node.entriesCount > 0 || node.children.length > 0 ? node : null;
};

const convertNodeToResourcesArray = (node) => {
  return [
    node.resource,
    ...node.children.flatMap((node) => convertNodeToResourcesArray(node)),
  ];
};
