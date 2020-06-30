export function getNotification(type, msg) {
  return {
    type: type,
    message: msg
  };
}

export function contains(arr, id) {
  let contains = false;
  for (let i = 0; i < arr.length; i++) {
    if (arr[i].id == id) {
      contains = true;
      break;
    }
  }
  return contains;
}

export function scrollTop() {
  document.body.scrollTop = 0;
  document.documentElement.scrollTop = 0;
}

export function scrollTopSmooth() {
  window.scrollTo({ top: 0, behavior: "smooth" });
}

export const Utils = {
  scrollTopSmooth: () => {
    window.scrollTo({ top: 0, behavior: "smooth" });
  }
};
