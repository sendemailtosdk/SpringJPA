sap.ui.define(["jquery.sap.global"], function (jQuery) {

	return {

		callService: function (sUrl, method, payload) {
			return new Promise(function (resolve, reject) {

				switch (method.toUpperCase()) {

					case "GET":
						jQuery.ajax(sUrl, {
							type: 'GET',
							success: function (data) {
								console.log(data);
								resolve(data);
							},
							error: function (err) {
								reject(err);
							}
						});
break;
					case "POST":
						jQuery.ajax(sUrl, {
							type: 'POST',
							contentType:"application/json",
							data:JSON.stringify(payload),
							success: function (data) {
								console.log(data);
								resolve(data);
							},
							error: function (err) {
								reject(err);
							}
						});

						break;


				}

			});
		}

	};


});