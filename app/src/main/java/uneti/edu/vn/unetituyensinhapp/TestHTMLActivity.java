package uneti.edu.vn.unetituyensinhapp;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;

public class TestHTMLActivity extends AppCompatActivity {
    private ImageView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_html);
        Intent tk=getIntent();
        String html1=tk.getStringExtra("cheese_name");
        String html=html1.replace("kt60",Character.toString((char)60));
          LinearLayout ll = (LinearLayout) findViewById(R.id.linear_layout_html);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Document document = Jsoup.parse(html);
        textView = (ImageView) findViewById(R.id.text_html);
        TextView tv = (TextView) findViewById(R.id.tv_ed);
        String src = null;
        String text = null;
        String align = null;

        Elements p = document.select("p");
        for (Element element : p) {
            if (element.select(":has(img)").isEmpty()) {
                if(element.text()!=null){
                    text = element.toString();
                    if(element.select("a[href]").isEmpty()){
                        if (element.select("p[style*=text-align: center]").isEmpty()) {
                            CardViewCustom card = new CardViewCustom(this);
                            TextView t = (TextView) card.findViewById(R.id.text_v);
                            t.setText(Html.fromHtml(text));
                            ll.addView(card, lp);
                        } else {

                            TextView k = new TextView(this);
                            k.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            k.setTextColor(getResources().getColor(R.color.colorPrimary));
                            k.setText(Html.fromHtml(text));
                            ll.addView(k, lp);
                        }
                    }else{
                        Element href=element.select("a[href]").first();
                        String linjk="http://khoacntt.uneti.edu.vn/"+href.attr("href");
                        String text1 = href.text();
                        String link="<a href=\""+linjk+"\">"+text1+"</a>";
                        TextView k = new TextView(this);
                        k.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        k.setTextColor(getResources().getColor(R.color.colorAccent));
                        k.setText(Html.fromHtml(link));
                        k.setMovementMethod(LinkMovementMethod.getInstance());
                        ll.addView(k, lp);
                    }
                }

            } else {
                Element image = element.select("img").first();
                src = image.attr("src");
                String u = "http://khoacntt.uneti.edu.vn/" + src;
                ImageView im = new ImageView(this);
                try {
                    Picasso.with(getApplicationContext()).load(u).placeholder(R.drawable.back_ground).error(R.drawable.back_ground).into(im);
                } catch (Exception ex) {
                    im.setImageResource(R.drawable.cheese_1);
                }
                ll.addView(im, lp);
            }
        }

    }
}
