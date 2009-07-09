



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
  <head>
    <meta http-equiv="content-type" content="text/html;charset=UTF-8" />
        <title>owlapi3/src/com/clarkparsia/owlwg/owlapi3/testcase/impl/OwlApi3xCTImpl.java at c7e1b122bd4b81e1c4fa2937230456fbf5ed2aa8 from msmithcp's owlwg-test - GitHub</title>
    <link rel="search" type="application/opensearchdescription+xml" href="/opensearch.xml" title="GitHub" />
    <link rel="fluid-icon" href="http://github.com/fluidicon.png" title="GitHub" />

    
      <link href="http://assets0.github.com/stylesheets/bundle.css?4e4549ef242cc20be2f40fdf0f47baaa74c05405" media="screen" rel="stylesheet" type="text/css" />
    

    
      
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
        <script src="http://assets3.github.com/javascripts/bundle.js?4e4549ef242cc20be2f40fdf0f47baaa74c05405" type="text/javascript"></script>
      
    
    
  
    
  

  <link href="http://github.com/feeds/msmithcp/commits/owlwg-test/c7e1b122bd4b81e1c4fa2937230456fbf5ed2aa8" rel="alternate" title="Recent Commits to owlwg-test:c7e1b122bd4b81e1c4fa2937230456fbf5ed2aa8" type="application/atom+xml" />

    <meta name="description" content="Software related to management and processing of OWL WG test cases" />


    

    <script type="text/javascript">
      github_user = 'bglimm'
    </script>
  </head>

  

  <body>
    

    <div id="main">
      <div id="header" class="">
        <div class="site">
          <div class="logo">
            <a href="http://github.com"><img src="/images/modules/header/logov3.png" alt="github" /></a>
          </div>
          
            <div class="topsearch">
  <form action="/search" id="top_search_form" method="get">
    <input type="search" class="search" name="q" /> <input type="submit" value="Search" />
    <input type="hidden" name="type" value="Everything" />
    <input type="hidden" name="repo" value="" />
    <input type="hidden" name="langOverride" value="" />
    <input type="hidden" name="start_value" value="1" />
  </form>
  <div class="links">
    <a href="/repositories">Browse</a> | <a href="/guides">Guides</a> | <a href="/search">Advanced</a>
  </div>
</div>
          
          
            
  <div class="corner userbox">
    <div class="box">
      <div class="gravatar">
        <a href="/"><img alt="" height="40" src="http://www.gravatar.com/avatar/beefe7ca86bbf9354709547899f6f275?s=40&amp;d=http%3A%2F%2Fgithub.com%2Fimages%2Fgravatars%2Fgravatar-40.png" width="40" /></a>
      </div>

      <div class="top">
        <div class="name">
          <a href="/">bglimm</a>
        </div>
        <div class="links">
          <a href="/account">account</a> |
          <a href="/bglimm">profile</a> |
          <a href="/logout">log out</a>
        </div>
      </div>

      <div class="bottom">
        <div class="select">
          <div class="site_links">
                        <a href="/">dashboard</a> | <a href="http://gist.github.com/mine">gists</a>
          </div>

          <form action="/search" class="search_repos" method="get" style="display:none;">
          <input id="q" name="q" size="18" type="search" /> 
          <input type="submit" value="Search" />
          <a href="#" class="cancel_search_link">x</a>
          </form>
        </div>
        
        <div class="inbox"> <strong><a href="/inbox">2</a></strong> </div>
      </div>
    </div>
  </div>

          
        </div>
      </div>

      
        
    <div id="repo_menu">
      <div class="site">
        <ul>
          
            <li class="active"><a href="http://github.com/msmithcp/owlwg-test/tree/">Source</a></li>

            <li class=""><a href="http://github.com/msmithcp/owlwg-test/commits/">Commits</a></li>

            
            <li class=""><a href="/msmithcp/owlwg-test/network">Network (2)</a></li>

            

            
              
              <li class=""><a href="/msmithcp/owlwg-test/issues">Issues (0)</a></li>
            

            
              
              <li class=""><a href="/msmithcp/owlwg-test/downloads">Downloads (0)</a></li>
            

            
              
              <li class=""><a href="http://wiki.github.com/msmithcp/owlwg-test">Wiki (1)</a></li>
            

            <li class=""><a href="/msmithcp/owlwg-test/graphs">Graphs</a></li>

            

          
        </ul>
      </div>
    </div>

  <div id="repo_sub_menu">
    <div class="site">
      <div class="joiner"></div>
      

      

      

      
    </div>
  </div>

  <div class="site">
    





<div id="repos">
  


<script type="text/javascript">
  GitHub.currentCommitRef = "c7e1b122bd4b81e1c4fa2937230456fbf5ed2aa8"
  GitHub.currentRepoOwner = "msmithcp"
  GitHub.currentRepo = "owlwg-test"
  
    GitHub.hasWriteAccess = false
    GitHub.watchingRepo = true
    GitHub.ignoredRepo = false
    GitHub.hasForkOfRepo = true
  
</script>



  <div class="repo public" id="repo_details">
    <div class="title">
      <div class="path">
        <a href="/msmithcp">msmithcp</a> / <b><a href="http://github.com/msmithcp/owlwg-test/tree">owlwg-test</a></b>

        

          <span id="edit_button" style="display:none;">
            <a href="/msmithcp/owlwg-test/edit"><img alt="edit" class="button" src="http://assets0.github.com/images/modules/repos/edit_button.png?4e4549ef242cc20be2f40fdf0f47baaa74c05405" /></a>
          </span>

          
            <span id="pull_request_button" style="display:none;">
              <a href="/msmithcp/owlwg-test/pull_request/" class="pull_request_button"><img alt="pull request" class="button" src="http://assets3.github.com/images/modules/repos/pull_request_button.png?4e4549ef242cc20be2f40fdf0f47baaa74c05405" /></a>
            </span>
            
            <span id="fast_forward_button" style="display:none;">
              <a href="/msmithcp/owlwg-test/fast_forward" id="ff_button"><img alt="fast forward" class="button" src="http://assets2.github.com/images/modules/repos/fast_forward_button.png?4e4549ef242cc20be2f40fdf0f47baaa74c05405" /></a>
            </span>

            <span id="fork_button">
              <a href="/msmithcp/owlwg-test/fork"><img alt="fork" class="button" src="http://assets3.github.com/images/modules/repos/fork_button.png?4e4549ef242cc20be2f40fdf0f47baaa74c05405" /></a>
            </span>
          

          <span id="watch_button">
            <a href="/msmithcp/owlwg-test/toggle_watch" class="toggle_watch"><img alt="watch" class="button" src="http://assets3.github.com/images/modules/repos/watch_button.png?4e4549ef242cc20be2f40fdf0f47baaa74c05405" /></a>
          </span>
          
          <span id="unwatch_button" style="display:none;">
            <a href="/msmithcp/owlwg-test/toggle_watch" class="toggle_watch"><img alt="watch" class="button" src="http://assets2.github.com/images/modules/repos/unwatch_button.png?4e4549ef242cc20be2f40fdf0f47baaa74c05405" /></a>
          </span>

          
            <a href="#" id="download_button" rel="msmithcp/owlwg-test"><img alt="download tarball" class="button" src="http://assets1.github.com/images/modules/repos/download_button.png?4e4549ef242cc20be2f40fdf0f47baaa74c05405" /></a>
          
        
      </div>

      <div class="security private_security" style="display:none">
        <a href="#private_repo" rel="facebox"><img src="/images/icons/private.png" alt="private" /></a>
      </div>

      <div id="private_repo" class="hidden">
        This repository is private.
        All pages are served over SSL and all pushing and pulling is done over SSH.
        No one may fork, clone, or view it unless they are added as a <a href="/msmithcp/owlwg-test/edit">member</a>.

        <br/>
        <br/>
        Every repository with this icon (<img src="/images/icons/private.png" alt="private" />) is private.
      </div>

      <div class="security public_security" style="">
        <a href="#public_repo" rel="facebox"><img src="/images/icons/public.png" alt="public" /></a>
      </div>

      <div id="public_repo" class="hidden">
        This repository is public.
        Anyone may fork, clone, or view it.

        <br/>
        <br/>
        Every repository with this icon (<img src="/images/icons/public.png" alt="public" />) is public.
      </div>

      

        <div class="flexipill">
          <a href="/msmithcp/owlwg-test/network">
          <table cellpadding="0" cellspacing="0">
            <tr><td><img alt="Forks" src="http://assets0.github.com/images/modules/repos/pills/forks.png?4e4549ef242cc20be2f40fdf0f47baaa74c05405" /></td><td class="middle"><span>2</span></td><td><img alt="Right" src="http://assets1.github.com/images/modules/repos/pills/right.png?4e4549ef242cc20be2f40fdf0f47baaa74c05405" /></td></tr>
          </table>
          </a>
        </div>

        <div class="flexipill">
          <a href="/msmithcp/owlwg-test/watchers">
          <table cellpadding="0" cellspacing="0">
            <tr><td><img alt="Watchers" src="http://assets3.github.com/images/modules/repos/pills/watchers.png?4e4549ef242cc20be2f40fdf0f47baaa74c05405" /></td><td class="middle"><span>5</span></td><td><img alt="Right" src="http://assets1.github.com/images/modules/repos/pills/right.png?4e4549ef242cc20be2f40fdf0f47baaa74c05405" /></td></tr>
          </table>
          </a>
        </div>
      </div>

    <div class="meta">
      <table>
        
        <tr>
          <td class="label">Description:</td>
          <td>
            <span id="repository_description" rel="/msmithcp/owlwg-test/edit/update">Software related to management and processing of OWL WG test cases</span>
            <a href="#description" class="edit_link action" style="display:none;">edit</a>
          </td>
        </tr>

        
            <tr>
              <td class="label">Homepage:</td>
              <td>
                                
                <span id="repository_homepage" rel="/msmithcp/owlwg-test/edit/update">
                  <a href="http://www.w3.org/2007/OWL/wiki/OWL_Working_Group">http://www.w3.org/2007/OWL/wiki/OWL_Working_Group</a>
                </span>
                <a href="#homepage" class="edit_link action" style="display:none;">edit</a>
              </td>
            </tr>

          
            <tr>
              <td class="label"><span id="public_clone_text" style="display:none;">Public&nbsp;</span>Clone&nbsp;URL:</td>
              
              <td>
                <a href="git://github.com/msmithcp/owlwg-test.git" class="git_url_facebox" rel="#git-clone">git://github.com/msmithcp/owlwg-test.git</a>
                      <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
              width="110"
              height="14"
              class="clippy"
              id="clippy" >
      <param name="movie" value="/flash/clippy.swf"/>
      <param name="allowScriptAccess" value="always" />
      <param name="quality" value="high" />
      <param name="scale" value="noscale" />
      <param NAME="FlashVars" value="text=git://github.com/msmithcp/owlwg-test.git">
      <param name="bgcolor" value="#F0F0F0">
      <param name="wmode" value="opaque">
      <embed src="/flash/clippy.swf"
             width="110"
             height="14"
             name="clippy"
             quality="high"
             allowScriptAccess="always"
             type="application/x-shockwave-flash"
             pluginspage="http://www.macromedia.com/go/getflashplayer"
             FlashVars="text=git://github.com/msmithcp/owlwg-test.git"
             bgcolor="#F0F0F0"
             wmode="opaque"
      />
      </object>

                <div id="git-clone" style="display:none;">
                  Give this clone URL to anyone.
                  <br/>
                  <code>git clone git://github.com/msmithcp/owlwg-test.git </code>
                </div>
              </td>
            </tr>
          
          
          <tr id="private_clone_url" style="display:none;">
            <td class="label">Your Clone URL:</td>
            
            <td>

              <div id="private-clone-url">
                <a href="git@github.com:msmithcp/owlwg-test.git" class="git_url_facebox" rel="#your-git-clone">git@github.com:msmithcp/owlwg-test.git</a>
                <input type="text" value="git@github.com:msmithcp/owlwg-test.git" style="display: none;" />
                      <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
              width="110"
              height="14"
              class="clippy"
              id="clippy" >
      <param name="movie" value="/flash/clippy.swf"/>
      <param name="allowScriptAccess" value="always" />
      <param name="quality" value="high" />
      <param name="scale" value="noscale" />
      <param NAME="FlashVars" value="text=git@github.com:msmithcp/owlwg-test.git">
      <param name="bgcolor" value="#F0F0F0">
      <param name="wmode" value="opaque">
      <embed src="/flash/clippy.swf"
             width="110"
             height="14"
             name="clippy"
             quality="high"
             allowScriptAccess="always"
             type="application/x-shockwave-flash"
             pluginspage="http://www.macromedia.com/go/getflashplayer"
             FlashVars="text=git@github.com:msmithcp/owlwg-test.git"
             bgcolor="#F0F0F0"
             wmode="opaque"
      />
      </object>

              </div>

              <div id="your-git-clone" style="display:none;">
                Use this clone URL yourself.
                <br/>
                <code>git clone git@github.com:msmithcp/owlwg-test.git </code>
              </div>
            </td>
          </tr>
          
          

          

          
      </table>

          </div>
  </div>






</div>


  <div id="commit">
    <div class="group">
        
  <div class="envelope commit">
    <div class="human">
      
        <div class="message"><pre><a href="/msmithcp/owlwg-test/commit/c7e1b122bd4b81e1c4fa2937230456fbf5ed2aa8">Static main to facilitate running Pellet OWLAPI 3 runner</a> </pre></div>
      

      <div class="actor">
        <div class="gravatar">
          
          <img alt="" height="30" src="http://www.gravatar.com/avatar/d01bec2157f5b650dabfc887df18641e?s=30&amp;d=http%3A%2F%2Fgithub.com%2Fimages%2Fgravatars%2Fgravatar-30.png" width="30" />
        </div>
        <div class="name">Mike Smith <span>(author)</span></div>
          <div class="date">
            <abbr class="relatize" title="2009-07-08 12:30:12">Wed Jul 08 12:30:12 -0700 2009</abbr> 
          </div>
      </div>
  
      
  
    </div>
    <div class="machine">
      <span>c</span>ommit&nbsp;&nbsp;<a href="/msmithcp/owlwg-test/commit/c7e1b122bd4b81e1c4fa2937230456fbf5ed2aa8" hotkey="c">c7e1b122bd4b81e1c4fa2937230456fbf5ed2aa8</a><br />
      <span>t</span>ree&nbsp;&nbsp;&nbsp;&nbsp;<a href="/msmithcp/owlwg-test/tree/c7e1b122bd4b81e1c4fa2937230456fbf5ed2aa8" hotkey="t">adeb582b10f5b8d381af67e91403648d5d98cfae</a><br />
  
      
        <span>p</span>arent&nbsp;
        
        <a href="/msmithcp/owlwg-test/tree/1896ecbd49ec0bd8b1a64173b9e0ffc1cbad508f" hotkey="p">1896ecbd49ec0bd8b1a64173b9e0ffc1cbad508f</a>
      
  
    </div>
  </div>

    </div>
  </div>



  
    <div id="path">
      <b><a href="/msmithcp/owlwg-test/tree">owlwg-test</a></b> / <a href="/msmithcp/owlwg-test/tree/c7e1b122bd4b81e1c4fa2937230456fbf5ed2aa8/owlapi3">owlapi3</a> / <a href="/msmithcp/owlwg-test/tree/c7e1b122bd4b81e1c4fa2937230456fbf5ed2aa8/owlapi3/src">src</a> / <a href="/msmithcp/owlwg-test/tree/c7e1b122bd4b81e1c4fa2937230456fbf5ed2aa8/owlapi3/src/com">com</a> / <a href="/msmithcp/owlwg-test/tree/c7e1b122bd4b81e1c4fa2937230456fbf5ed2aa8/owlapi3/src/com/clarkparsia">clarkparsia</a> / <a href="/msmithcp/owlwg-test/tree/c7e1b122bd4b81e1c4fa2937230456fbf5ed2aa8/owlapi3/src/com/clarkparsia/owlwg">owlwg</a> / <a href="/msmithcp/owlwg-test/tree/c7e1b122bd4b81e1c4fa2937230456fbf5ed2aa8/owlapi3/src/com/clarkparsia/owlwg/owlapi3">owlapi3</a> / <a href="/msmithcp/owlwg-test/tree/c7e1b122bd4b81e1c4fa2937230456fbf5ed2aa8/owlapi3/src/com/clarkparsia/owlwg/owlapi3/testcase">testcase</a> / <a href="/msmithcp/owlwg-test/tree/c7e1b122bd4b81e1c4fa2937230456fbf5ed2aa8/owlapi3/src/com/clarkparsia/owlwg/owlapi3/testcase/impl">impl</a> / OwlApi3xCTImpl.java       <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
              width="110"
              height="14"
              class="clippy"
              id="clippy" >
      <param name="movie" value="/flash/clippy.swf"/>
      <param name="allowScriptAccess" value="always" />
      <param name="quality" value="high" />
      <param name="scale" value="noscale" />
      <param NAME="FlashVars" value="text=owlapi3/src/com/clarkparsia/owlwg/owlapi3/testcase/impl/OwlApi3xCTImpl.java">
      <param name="bgcolor" value="#FFFFFF">
      <param name="wmode" value="opaque">
      <embed src="/flash/clippy.swf"
             width="110"
             height="14"
             name="clippy"
             quality="high"
             allowScriptAccess="always"
             type="application/x-shockwave-flash"
             pluginspage="http://www.macromedia.com/go/getflashplayer"
             FlashVars="text=owlapi3/src/com/clarkparsia/owlwg/owlapi3/testcase/impl/OwlApi3xCTImpl.java"
             bgcolor="#FFFFFF"
             wmode="opaque"
      />
      </object>

    </div>

    <div id="files">
      <div class="file">
        <div class="meta">
          <div class="info">
            <span>100644</span>
            <span>71 lines (60 sloc)</span>
            <span>2.162 kb</span>
          </div>
          <div class="actions">
            
            <a href="/msmithcp/owlwg-test/raw/c7e1b122bd4b81e1c4fa2937230456fbf5ed2aa8/owlapi3/src/com/clarkparsia/owlwg/owlapi3/testcase/impl/OwlApi3xCTImpl.java" id="raw-url">raw</a>
            
              <a href="/msmithcp/owlwg-test/blame/c7e1b122bd4b81e1c4fa2937230456fbf5ed2aa8/owlapi3/src/com/clarkparsia/owlwg/owlapi3/testcase/impl/OwlApi3xCTImpl.java">blame</a>
            
            <a href="/msmithcp/owlwg-test/commits/master/owlapi3/src/com/clarkparsia/owlwg/owlapi3/testcase/impl/OwlApi3xCTImpl.java">history</a>
          </div>
        </div>
        
  <div class="data syntax">
    
      <table cellpadding="0" cellspacing="0">
        <tr>
          <td>
            
            <pre class="line_numbers">
<span id="LID1" rel="#L1">1</span>
<span id="LID2" rel="#L2">2</span>
<span id="LID3" rel="#L3">3</span>
<span id="LID4" rel="#L4">4</span>
<span id="LID5" rel="#L5">5</span>
<span id="LID6" rel="#L6">6</span>
<span id="LID7" rel="#L7">7</span>
<span id="LID8" rel="#L8">8</span>
<span id="LID9" rel="#L9">9</span>
<span id="LID10" rel="#L10">10</span>
<span id="LID11" rel="#L11">11</span>
<span id="LID12" rel="#L12">12</span>
<span id="LID13" rel="#L13">13</span>
<span id="LID14" rel="#L14">14</span>
<span id="LID15" rel="#L15">15</span>
<span id="LID16" rel="#L16">16</span>
<span id="LID17" rel="#L17">17</span>
<span id="LID18" rel="#L18">18</span>
<span id="LID19" rel="#L19">19</span>
<span id="LID20" rel="#L20">20</span>
<span id="LID21" rel="#L21">21</span>
<span id="LID22" rel="#L22">22</span>
<span id="LID23" rel="#L23">23</span>
<span id="LID24" rel="#L24">24</span>
<span id="LID25" rel="#L25">25</span>
<span id="LID26" rel="#L26">26</span>
<span id="LID27" rel="#L27">27</span>
<span id="LID28" rel="#L28">28</span>
<span id="LID29" rel="#L29">29</span>
<span id="LID30" rel="#L30">30</span>
<span id="LID31" rel="#L31">31</span>
<span id="LID32" rel="#L32">32</span>
<span id="LID33" rel="#L33">33</span>
<span id="LID34" rel="#L34">34</span>
<span id="LID35" rel="#L35">35</span>
<span id="LID36" rel="#L36">36</span>
<span id="LID37" rel="#L37">37</span>
<span id="LID38" rel="#L38">38</span>
<span id="LID39" rel="#L39">39</span>
<span id="LID40" rel="#L40">40</span>
<span id="LID41" rel="#L41">41</span>
<span id="LID42" rel="#L42">42</span>
<span id="LID43" rel="#L43">43</span>
<span id="LID44" rel="#L44">44</span>
<span id="LID45" rel="#L45">45</span>
<span id="LID46" rel="#L46">46</span>
<span id="LID47" rel="#L47">47</span>
<span id="LID48" rel="#L48">48</span>
<span id="LID49" rel="#L49">49</span>
<span id="LID50" rel="#L50">50</span>
<span id="LID51" rel="#L51">51</span>
<span id="LID52" rel="#L52">52</span>
<span id="LID53" rel="#L53">53</span>
<span id="LID54" rel="#L54">54</span>
<span id="LID55" rel="#L55">55</span>
<span id="LID56" rel="#L56">56</span>
<span id="LID57" rel="#L57">57</span>
<span id="LID58" rel="#L58">58</span>
<span id="LID59" rel="#L59">59</span>
<span id="LID60" rel="#L60">60</span>
<span id="LID61" rel="#L61">61</span>
<span id="LID62" rel="#L62">62</span>
<span id="LID63" rel="#L63">63</span>
<span id="LID64" rel="#L64">64</span>
<span id="LID65" rel="#L65">65</span>
<span id="LID66" rel="#L66">66</span>
<span id="LID67" rel="#L67">67</span>
<span id="LID68" rel="#L68">68</span>
<span id="LID69" rel="#L69">69</span>
<span id="LID70" rel="#L70">70</span>
<span id="LID71" rel="#L71">71</span>
</pre>
          </td>
          <td width="100%">
            
            
              <div class="highlight"><pre><div class="line" id="LC1"><span class="kn">package</span> <span class="n">com</span><span class="o">.</span><span class="na">clarkparsia</span><span class="o">.</span><span class="na">owlwg</span><span class="o">.</span><span class="na">owlapi3</span><span class="o">.</span><span class="na">testcase</span><span class="o">.</span><span class="na">impl</span><span class="o">;</span></div><div class="line" id="LC2">&nbsp;</div><div class="line" id="LC3"><span class="kn">import</span> <span class="nn">java.util.EnumMap</span><span class="o">;</span></div><div class="line" id="LC4">&nbsp;</div><div class="line" id="LC5"><span class="kn">import</span> <span class="nn">org.semanticweb.owlapi.apibinding.OWLManager</span><span class="o">;</span></div><div class="line" id="LC6"><span class="kn">import</span> <span class="nn">org.semanticweb.owlapi.io.StringInputSource</span><span class="o">;</span></div><div class="line" id="LC7"><span class="kn">import</span> <span class="nn">org.semanticweb.owlapi.model.OWLOntology</span><span class="o">;</span></div><div class="line" id="LC8"><span class="kn">import</span> <span class="nn">org.semanticweb.owlapi.model.OWLOntologyCreationException</span><span class="o">;</span></div><div class="line" id="LC9"><span class="kn">import</span> <span class="nn">org.semanticweb.owlapi.model.OWLOntologyManager</span><span class="o">;</span></div><div class="line" id="LC10">&nbsp;</div><div class="line" id="LC11"><span class="kn">import</span> <span class="nn">com.clarkparsia.owlwg.testcase.AbstractPremisedTest</span><span class="o">;</span></div><div class="line" id="LC12"><span class="kn">import</span> <span class="nn">com.clarkparsia.owlwg.testcase.OntologyParseException</span><span class="o">;</span></div><div class="line" id="LC13"><span class="kn">import</span> <span class="nn">com.clarkparsia.owlwg.testcase.PremisedTest</span><span class="o">;</span></div><div class="line" id="LC14"><span class="kn">import</span> <span class="nn">com.clarkparsia.owlwg.testcase.SerializationFormat</span><span class="o">;</span></div><div class="line" id="LC15">&nbsp;</div><div class="line" id="LC16"><span class="c">/**</span></div><div class="line" id="LC17"><span class="c"> * &lt;p&gt;</span></div><div class="line" id="LC18"><span class="c"> * Title: OWLAPIv3 xConsistency Test Case Base Class</span></div><div class="line" id="LC19"><span class="c"> * &lt;/p&gt;</span></div><div class="line" id="LC20"><span class="c"> * &lt;p&gt;</span></div><div class="line" id="LC21"><span class="c"> * Description: Extended for consistency and inconsistency cases</span></div><div class="line" id="LC22"><span class="c"> * &lt;/p&gt;</span></div><div class="line" id="LC23"><span class="c"> * &lt;p&gt;</span></div><div class="line" id="LC24"><span class="c"> * Copyright: Copyright &amp;copy; 2009</span></div><div class="line" id="LC25"><span class="c"> * &lt;/p&gt;</span></div><div class="line" id="LC26"><span class="c"> * &lt;p&gt;</span></div><div class="line" id="LC27"><span class="c"> * Company: Clark &amp; Parsia, LLC. &lt;a</span></div><div class="line" id="LC28"><span class="c"> * href=&quot;http://clarkparsia.com/&quot;/&gt;http://clarkparsia.com/&lt;/a&gt;</span></div><div class="line" id="LC29"><span class="c"> * &lt;/p&gt;</span></div><div class="line" id="LC30"><span class="c"> * </span></div><div class="line" id="LC31"><span class="c"> * @author Mike Smith &amp;lt;msmith@clarkparsia.com&amp;gt;</span></div><div class="line" id="LC32"><span class="c"> */</span></div><div class="line" id="LC33"><span class="kd">public</span> <span class="kd">abstract</span> <span class="kd">class</span> <span class="nc">OwlApi3xCTImpl</span> <span class="kd">extends</span> <span class="n">AbstractPremisedTest</span><span class="o">&lt;</span><span class="n">OWLOntology</span><span class="o">&gt;</span> <span class="kd">implements</span></div><div class="line" id="LC34">&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">PremisedTest</span><span class="o">&lt;</span><span class="n">OWLOntology</span><span class="o">&gt;,</span> <span class="n">OwlApi3Case</span> <span class="o">{</span></div><div class="line" id="LC35">&nbsp;</div><div class="line" id="LC36">&nbsp;&nbsp;<span class="kd">private</span> <span class="kd">final</span> <span class="n">OWLOntologyManager</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">manager</span><span class="o">;</span></div><div class="line" id="LC37">&nbsp;&nbsp;<span class="kd">private</span> <span class="kd">final</span> <span class="n">EnumMap</span><span class="o">&lt;</span><span class="n">SerializationFormat</span><span class="o">,</span> <span class="n">OWLOntology</span><span class="o">&gt;</span>&nbsp;&nbsp;<span class="n">parsedPremise</span><span class="o">;</span></div><div class="line" id="LC38">&nbsp;</div><div class="line" id="LC39">&nbsp;&nbsp;<span class="kd">public</span> <span class="nf">OwlApi3xCTImpl</span><span class="o">(</span><span class="n">org</span><span class="o">.</span><span class="na">semanticweb</span><span class="o">.</span><span class="na">owl</span><span class="o">.</span><span class="na">model</span><span class="o">.</span><span class="na">OWLOntology</span> <span class="n">ontology</span><span class="o">,</span></div><div class="line" id="LC40">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">org</span><span class="o">.</span><span class="na">semanticweb</span><span class="o">.</span><span class="na">owl</span><span class="o">.</span><span class="na">model</span><span class="o">.</span><span class="na">OWLIndividual</span> <span class="n">i</span><span class="o">)</span> <span class="o">{</span></div><div class="line" id="LC41">&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">super</span><span class="o">(</span> <span class="n">ontology</span><span class="o">,</span> <span class="n">i</span> <span class="o">);</span></div><div class="line" id="LC42">&nbsp;</div><div class="line" id="LC43">&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">parsedPremise</span> <span class="o">=</span> <span class="k">new</span> <span class="n">EnumMap</span><span class="o">&lt;</span><span class="n">SerializationFormat</span><span class="o">,</span> <span class="n">OWLOntology</span><span class="o">&gt;(</span> <span class="n">SerializationFormat</span><span class="o">.</span><span class="na">class</span> <span class="o">);</span></div><div class="line" id="LC44">&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">manager</span> <span class="o">=</span> <span class="n">OWLManager</span><span class="o">.</span><span class="na">createOWLOntologyManager</span><span class="o">();</span></div><div class="line" id="LC45">&nbsp;&nbsp;<span class="o">}</span></div><div class="line" id="LC46">&nbsp;</div><div class="line" id="LC47">&nbsp;&nbsp;<span class="kd">public</span> <span class="n">OWLOntologyManager</span> <span class="nf">getOWLOntologyManager</span><span class="o">()</span> <span class="o">{</span></div><div class="line" id="LC48">&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">return</span> <span class="n">manager</span><span class="o">;</span></div><div class="line" id="LC49">&nbsp;&nbsp;<span class="o">}</span></div><div class="line" id="LC50">&nbsp;</div><div class="line" id="LC51">&nbsp;&nbsp;<span class="kd">public</span> <span class="n">OWLOntology</span> <span class="nf">parsePremiseOntology</span><span class="o">(</span><span class="n">SerializationFormat</span> <span class="n">format</span><span class="o">)</span></div><div class="line" id="LC52">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="kd">throws</span> <span class="n">OntologyParseException</span> <span class="o">{</span></div><div class="line" id="LC53">&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">try</span> <span class="o">{</span></div><div class="line" id="LC54">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">ImportsHelper</span><span class="o">.</span><span class="na">loadImports</span><span class="o">(</span> <span class="k">this</span> <span class="o">);</span></div><div class="line" id="LC55">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">OWLOntology</span> <span class="n">o</span> <span class="o">=</span> <span class="n">parsedPremise</span><span class="o">.</span><span class="na">get</span><span class="o">(</span> <span class="n">format</span> <span class="o">);</span></div><div class="line" id="LC56">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">if</span><span class="o">(</span> <span class="n">o</span> <span class="o">==</span> <span class="kc">null</span> <span class="o">)</span> <span class="o">{</span></div><div class="line" id="LC57">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">String</span> <span class="n">l</span> <span class="o">=</span> <span class="n">getPremiseOntology</span><span class="o">(</span> <span class="n">format</span> <span class="o">);</span></div><div class="line" id="LC58">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">if</span><span class="o">(</span> <span class="n">l</span> <span class="o">==</span> <span class="kc">null</span> <span class="o">)</span></div><div class="line" id="LC59">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">return</span> <span class="kc">null</span><span class="o">;</span></div><div class="line" id="LC60">&nbsp;</div><div class="line" id="LC61">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">StringInputSource</span> <span class="n">source</span> <span class="o">=</span> <span class="k">new</span> <span class="n">StringInputSource</span><span class="o">(</span> <span class="n">l</span> <span class="o">);</span></div><div class="line" id="LC62">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">o</span> <span class="o">=</span> <span class="n">getOWLOntologyManager</span><span class="o">().</span><span class="na">loadOntology</span><span class="o">(</span> <span class="n">source</span> <span class="o">);</span></div><div class="line" id="LC63">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="n">parsedPremise</span><span class="o">.</span><span class="na">put</span><span class="o">(</span> <span class="n">format</span><span class="o">,</span> <span class="n">o</span> <span class="o">);</span></div><div class="line" id="LC64">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class="line" id="LC65">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">return</span> <span class="n">o</span><span class="o">;</span></div><div class="line" id="LC66">&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span> <span class="k">catch</span><span class="o">(</span> <span class="n">OWLOntologyCreationException</span> <span class="n">e</span> <span class="o">)</span> <span class="o">{</span></div><div class="line" id="LC67">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class="k">throw</span> <span class="k">new</span> <span class="nf">OntologyParseException</span><span class="o">(</span> <span class="n">e</span> <span class="o">);</span></div><div class="line" id="LC68">&nbsp;&nbsp;&nbsp;&nbsp;<span class="o">}</span></div><div class="line" id="LC69">&nbsp;&nbsp;<span class="o">}</span></div><div class="line" id="LC70"><span class="o">}</span></div><div class="line" id="LC71">&nbsp;</div></pre></div>
            
          </td>
        </tr>
      </table>
    
  </div>


      </div>
    </div>
    
  


  </div>

      

      <div class="push"></div>
    </div>

    <div id="footer">
      <div class="site">
        <div class="info">
          <div class="links">
            <a href="http://github.com/blog/148-github-shirts-now-available">Shirts</a> |
            <a href="http://github.com/blog">Blog</a> |
            <a href="http://support.github.com/">Support</a> |
            <a href="http://github.com/training">Training</a> |
            <a href="http://github.com/contact">Contact</a> |
            <a href="http://groups.google.com/group/github/">Google Group</a> |
            <a href="http://develop.github.com">API</a> |
            <a href="http://twitter.com/github">Status</a>
          </div>
          <div class="company">
            <span id="_rrt" title="0.08403s from xc88-s00008">GitHub</span>&trade;
            is <a href="http://logicalawesome.com/">Logical Awesome</a> &copy;2009 | <a href="/site/terms">Terms of Service</a> | <a href="/site/privacy">Privacy Policy</a>
          </div>
        </div>
        <div class="sponsor">
          <a href="http://engineyard.com"><img src="/images/modules/footer/ey-rubyhosting.png" alt="Engine Yard" /></a>
        </div>
      </div>
    </div>

    <div id="coming_soon" style="display:none;">
      This feature is coming soon.  Sit tight!
    </div>

    
        <script type="text/javascript">
    var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
    document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
    </script>
    <script type="text/javascript">
    var pageTracker = _gat._getTracker("UA-3769691-2");
    pageTracker._initData();
    pageTracker._trackPageview();
    </script>

    
  </body>
</html>

