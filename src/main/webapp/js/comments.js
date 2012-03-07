$(function() {
	Recaptcha.create("${recaptcha_public_key}",
			"recaptcha",
			{
				theme: "white",
				lang : 'en',
				callback: function() {
					$('#recaptcha_response_field').attr('name', 'recaptchaResponse');
					$('#recaptcha_challenge_field').attr('name', 'recaptchaChallenge');
				}
			}
	);
	$('form').validate({
		rules: {
			recaptcha_response_field: 'required'
		}
	});
});