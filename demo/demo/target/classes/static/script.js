function search() {
    const keyword = document.getElementById("search-box").value;
    fetch(`/search?keyword=${encodeURIComponent(keyword)}`)
        .then(response => response.json())
        .then(data => {
            const resultsDiv = document.getElementById("results");
            resultsDiv.innerHTML = "";
            for (let title in data) {
                const link = data[title];
                resultsDiv.innerHTML += `<div><a href="${link}" target="_blank">${title}</a></div>`;
            }
        })
        .catch(error => {
            console.error("Error fetching results:", error);
        });
}
