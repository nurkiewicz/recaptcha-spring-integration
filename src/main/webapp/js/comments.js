$(function() {
	Recaptcha.create("${recaptcha_public_key}",
			"recaptcha",
			{
				theme: "white",
				lang : 'en'
			}
	);
	$('form').validate({
		rules: {
			recaptcha_response_field: 'required'
		}
	});
});