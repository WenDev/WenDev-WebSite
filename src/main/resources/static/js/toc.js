function toc() {
  // Find the TOC container element.
  // If there isn't one, create one at the start of the document.
  let toc = document.getElementById("TOC");
  let toc_group = document.getElementById("category-item-group");
  if (!toc) {
    toc = document.createElement("div");
    toc.id = "TOC";
    document.body.insertBefore(toc, document.body.firstChild);
  }
  // Find all section heading elements
  let headings;
  if (document.querySelectorAll) // Can we do it the easy way?
    headings = document.querySelectorAll(".article h1, .article h2, .article h3, " +
      ".article h4, .article h5, .article h6");
  else   // Otherwise, find the headings the hard way
    headings = findHeadings(document.body, []);

  // Recursively traverse the document body looking for headings
  function findHeadings(root, sects) {
    for (let c = root.firstChild; c != null; c = c.nextSibling) {
      if (c.nodeType !== 1) continue;
      if (c.tagName.length === 2 && c.tagName.charAt(0) === "H")
        sects.push(c);
      else
        findHeadings(c, sects);
    }
    return sects;
  }

  // Initialize an array that keeps track of section numbers.
  let sectionNumbers = [0, 0, 0, 0, 0, 0];
  // Now loop through the section header elements we found.
  for (let h = 0; h < headings.length; h++) {
    let heading = headings[h];
    // Skip the section heading if it is inside the TOC container.
    if (heading.parentNode === toc) continue;
    // Figure out what level heading it is.
    let level = parseInt(heading.tagName.charAt(1));
    if (isNaN(level) || level < 1 || level > 6) continue;
    // Increment the section number for this heading level
    // and reset all lower heading level numbers to zero.
    sectionNumbers[level - 1]++;
    for (let i = level; i < 6; i++) sectionNumbers[i] = 0;
    // Now combine section numbers for all heading levels
    // to produce a section number like 2.3.1.
    let sectionNumber = sectionNumbers.slice(0, level).join(".");
    // Add the section number to the section header title.
    // We place the number in a <span> to make it styleable.
    let span = document.createElement("span");
    span.className = "TOCSectNum";
    // span.innerHTML = sectionNumber + " ";
    heading.insertBefore(span, heading.firstChild);
    // Wrap the heading in a named anchor so we can link to it.
    let anchor = document.createElement("a");
    anchor.name = "TOC" + sectionNumber;
    anchor.id = "TOC" + sectionNumber;
    heading.parentNode.insertBefore(anchor, heading);
    anchor.appendChild(heading);
    // Now create a link to this section.
    let link = document.createElement("a");
    link.href = "#TOC" + sectionNumber; // Link destination
    link.innerHTML = heading.innerHTML; // Link text is same as heading
    // Place the link in a div that is styleable based on the level.
    let entry = document.createElement("li");
    entry.className = "category-item TOCEntry TOCLevel" + level;
    entry.appendChild(link);
    // And add the div to the TOC container.
    toc_group.appendChild(entry);
  }
}

window.onload = function () {
  let codeBlocks = document.querySelectorAll('pre')
  for (let i = 0; i < codeBlocks.length; i++) {
    codeBlocks[i].setAttribute('class', 'prettyprint linenums')
  }
  toc();
  prettyPrint();
}