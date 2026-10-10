// Part 1: FAQ Dataset (প্রশ্ন ও উত্তরের তালিকা)
const faqData = [
    {
        keywords: ["delivery", "time", "hours", "when", "open"],
        question: "What are your delivery hours?",
        answer: "Our delivery hours are 9 AM to 8 PM, Sunday through Thursday."
    },
    {
        keywords: ["shipping", "long", "take", "days", "delivery time"],
        question: "How long does shipping take?",
        answer: "Standard shipping takes 3-5 business days. Express shipping takes 1-2 days."
    },
    {
        keywords: ["return", "refund", "policy", "back"],
        question: "What is your return policy?",
        answer: "You can return any product within 30 days of purchase with a valid receipt."
    },
    {
        keywords: ["track", "order", "status", "where"],
        question: "How can I track my order?",
        answer: "You can track your order using the tracking ID sent to your email."
    },
    {
        keywords: ["payment", "pay", "card", "cash", "bkash"],
        question: "What payment methods do you accept?",
        answer: "We accept Credit/Debit cards, PayPal, bKash, and Cash on Delivery."
    }
];

// Part 2: Text Preprocessing
function preprocessText(text) {
    return text.toLowerCase().replace(/[^\w\s]/gi, ''); // পাঙ্কচুয়েশন মুছে ফেলা ও লোয়ারকেস করা
}

// Part 3: Intent / Question Matching Logic
function getBestMatch(userInput) {
    const cleanedInput = preprocessText(userInput);
    const words = cleanedInput.split(' ');

    let bestScore = 0;
    let bestAnswer = "দুঃখিত, আমি আপনার প্রশ্নটি বুঝতে পারিনি। অনুগ্রহ করে অন্যভাবে জিজ্ঞাসা করুন অথবা সাপোর্টে যোগাযোগ করুন।";

    faqData.forEach(item => {
        let matchCount = 0;

        // চেক করা কতগুলো কি-ওয়ার্ড মিলছে
        item.keywords.forEach(keyword => {
            if (cleanedInput.includes(keyword)) {
                matchCount++;
            }
        });

        // সেরা স্কোর আপডেট
        if (matchCount > bestScore) {
            bestScore = matchCount;
            bestAnswer = item.answer;
        }
    });

    return bestAnswer;
}

// Part 4 & 5: UI Handling Function
function sendMessage() {
    const inputField = document.getElementById("userInput");
    const userText = inputField.value.trim();

    if (userText === "") return;

    // ১. ইউজারের মেসেজ স্ক্রিনে দেখান
    appendMessage(userText, "user-message");
    inputField.value = "";

    // ২. চ্যাটবটের উত্তর বের করা ও স্ক্রিনে দেখানো (সামান্য ডিল্যে সহ)
    setTimeout(() => {
        const botResponse = getBestMatch(userText);
        appendMessage(botResponse, "bot-message");
    }, 500);
}

function appendMessage(text, className) {
    const chatBox = document.getElementById("chatBox");
    const messageDiv = document.createElement("div");
    messageDiv.className = `message ${className}`;
    messageDiv.innerText = text;
    chatBox.appendChild(messageDiv);
    
    // অটো স্ক্রোল ডাউন
    chatBox.scrollTop = chatBox.scrollHeight;
}

// Enter কী চাপলে পাঠাবে
function handleKeyPress(event) {
    if (event.key === "Enter") {
        sendMessage();
    }
}