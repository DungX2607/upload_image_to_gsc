document.getElementById('uploadForm').addEventListener('submit', function(e) {
    e.preventDefault();
    const fileInput = document.getElementById('fileInput');
    const statusText = document.getElementById('uploadStatus');
    const imagesList = document.getElementById('imagesList');

    if (fileInput.files.length > 0) {
        const file = fileInput.files[0];
        const reader = new FileReader();
        
        reader.onload = function(e) {
            // Display the image in the list after upload is successful
            const imgElement = document.createElement('img');
            imgElement.src = e.target.result;
            imgElement.style.maxWidth = '100%';
            imgElement.alt = 'Uploaded Image';

            const li = document.createElement('li');
            li.appendChild(imgElement);
            imagesList.appendChild(li);
        };

        reader.readAsDataURL(file);

        const formData = new FormData();
        formData.append('file', file);

        fetch('http://localhost:8080/upload', {
            method: 'POST',
            body: formData,
        })
        .then(response => response.text())
        .then(data => {
            statusText.innerText = "Upload successful! File URL: " + data;
            console.log('Success:', data);
        })
        .catch((error) => {
            statusText.innerText = "Upload failed. Please try again.";
            console.error('Error:', error);
        });
    } else {
        statusText.innerText = "Please select a file to upload.";
    }
});
