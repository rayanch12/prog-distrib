// Configuration de l'URL du backend
const BACKEND_URL = 'http://backend-service';

// Fonction pour ajouter un nombre
async function addNumber() {
    const input = document.getElementById('numberInput');
    const number = parseInt(input.value);

    if (isNaN(number)) {
        alert('Veuillez entrer un nombre valide');
        return;
    }

    try {
        const response = await fetch('/api/numbers', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ value: number })
        });

        if (response.ok) {
            input.value = '';
            await refreshNumbers();
        } else {
            alert('Erreur lors de l\'ajout du nombre');
        }
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur de connexion au serveur');
    }
}

// Fonction pour rafraîchir la liste des nombres
async function refreshNumbers() {
    try {
        const response = await fetch('/api/numbers');
        const numbers = await response.json();
        
        const numbersList = document.getElementById('numbersList');
        numbersList.innerHTML = '';

        numbers.forEach(number => {
            const numberElement = document.createElement('div');
            numberElement.className = 'number-item';
            numberElement.textContent = number.value;
            numbersList.appendChild(numberElement);
        });
    } catch (error) {
        console.error('Erreur:', error);
        alert('Erreur lors de la récupération des nombres');
    }
}

// Rafraîchir la liste au chargement de la page
document.addEventListener('DOMContentLoaded', refreshNumbers);

// Ajouter un événement pour la touche Entrée sur l'input
document.getElementById('numberInput').addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        addNumber();
    }
});
