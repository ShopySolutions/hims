(function() {
    'use strict';

    angular
        .module('hmisApp')
        .filter('findLanguageFromKey', findLanguageFromKey)
        .filter('findLanguageRtlFromKey', findLanguageRtlFromKey);

    var languages = {
        'ar-ly': { name: 'العربية', rtl: true },
        'hy': { name: 'Հայերեն' },
        'ca': { name: 'Català' },
        'zh-cn': { name: '中文（简体）' },
        'zh-tw': { name: '繁體中文' },
        'cs': { name: 'Český' },
        'da': { name: 'Dansk' },
        'nl': { name: 'Nederlands' },
        'en': { name: 'English' },
        'et': { name: 'Eesti' },
        'fa': { name: 'فارسی', rtl: true }
        // jhipster-needle-i18n-language-key-pipe - JHipster will add/remove languages in this object
    };

    function findLanguageFromKey() {
        return findLanguageFromKeyFilter;

        function findLanguageFromKeyFilter(lang) {
            return languages[lang].name;
        }
    }

    function findLanguageRtlFromKey() {
        return findLanguageRtlFromKeyFilter;

        function findLanguageRtlFromKeyFilter(lang) {
            return languages[lang].rtl;
        }
    }

})();
