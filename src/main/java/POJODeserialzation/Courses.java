package POJODeserialzation;

import java.util.List;

public class Courses {
	private List<Api> api;
	private List<WebAutomation> webautomatiom;
	private List<Mobile> mobile;

	public List<Api> getApi() {
		return api;
	}

	public void setApi(List<Api> api) {
		this.api = api;
	}

	public List<WebAutomation> getWebautomatiom() {
		return webautomatiom;
	}

	public void setWebautomatiom(List<WebAutomation> webautomatiom) {
		this.webautomatiom = webautomatiom;
	}

	public List<Mobile> getMobile() {
		return mobile;
	}

	public void setMobile(List<Mobile> mobile) {
		this.mobile = mobile;
	}

}
