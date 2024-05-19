document.querySelector('.pageSelectionForm').addEventListener('submit', function(e) {
    e.preventDefault();
    let pageNum = parseInt(document.querySelector('input[name="page"]').value);
    let family = document.querySelector('input[name="family"]').value;
    let genus = document.querySelector('input[name="genus"]').value;
    let specie = document.querySelector('input[name="specie"]').value;
    if (!isNaN(pageNum) && pageNum > 0 && (family.trim() !== '' || genus.trim() !== '' || specie.trim() !== '')) {
        pageNum -= 1; // Пользователи вводят страницы, начиная с 1, но нам нужно начинать с 0
        window.location.href = '/collection?family=' + family + '&genus=' + genus + '&specie=' + specie + '&page=' + pageNum;
    } else if (!isNaN(pageNum) && pageNum > 0 && family.trim() === '' && genus.trim() === '' && specie.trim() === '') {
        pageNum -= 1;
        window.location.href = '/collection?page=' + pageNum;
    }
});
function resetForm() {
    document.getElementById('family').value = '';
    document.getElementById('genus').value = '';
    document.getElementById('specie').value = '';
}